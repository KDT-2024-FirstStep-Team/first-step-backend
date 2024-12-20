package com.kdt.firststep.user.service;

import com.kdt.firststep.config.JwtTokenProvider;
import com.kdt.firststep.counselor.domain.CounselingReservation;
import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.domain.SavedCounselor;
import com.kdt.firststep.file.S3FileService;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.dto.JoinDTO;
import com.kdt.firststep.user.dto.LoginRequestDTO;
import com.kdt.firststep.user.dto.request.UpdateProfileRequestDTO;
import com.kdt.firststep.user.dto.response.CounselorReservationDTO;
import com.kdt.firststep.user.dto.response.SavedCounselorResponseDTO;
import com.kdt.firststep.user.dto.response.UpdateProfileResponseDTO;
import com.kdt.firststep.user.dto.response.UserActivityDTO;
import com.kdt.firststep.user.repository.CounselingReservationRepository2;
import com.kdt.firststep.user.repository.SavedCounselorRepository;
import com.kdt.firststep.user.repository.UserRepository;
import com.mchange.util.DuplicateElementException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SavedCounselorRepository savedCounselorRepository;
    private final CounselingReservationRepository2 counselingReservationRepository;
    private final S3FileService s3FileService;

    @Override
    public void joinUser(JoinDTO joinDTO) {
        userRepository.findByNickname(joinDTO.getNickname()).ifPresent(users -> {
            throw new DuplicateElementException("Nickname already exists");
        });
        log.info("닉네임 중복 확인 완료", joinDTO.getNickname());
        log.info(joinDTO.getNickname());
        userRepository.save(Users.builder()
                .username(joinDTO.getUsername())
                .nickname(joinDTO.getNickname())
                .email(joinDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .birth(joinDTO.getBirth())
                .gender(joinDTO.isGender())
                .phoneNumber(joinDTO.getPhoneNumber())
                .personalityCheck(false)
                .coupleCheck(false)
                .counselorCheck(false)
                .maritalStatus(false)
                .build());

        log.info("회원가입 완료: {}", joinDTO);
    }

    @Override
    public void login(LoginRequestDTO loginRequestDTO) {
        Users user = userRepository.findByEmail(loginRequestDTO.getUserEmail())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        validatePassword(loginRequestDTO.getPassword(), user.getPassword());

        List<String> roles = getUserRoles(user);

        jwtTokenProvider.generateToken(user.getEmail(), user.getNickname(), roles);

    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!bCryptPasswordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    private List<String> getUserRoles(Users user) {
        List<String> roles = new ArrayList<>();
        if (user.getCounselorCheck()) {
            roles.add("ROLE_COUNSELOR");
        }
        if (user.getCoupleCheck()) {
            roles.add("ROLE_COUPLE");
        }
        roles.add("ROLE_USER"); // 기본 권한
        return roles;
    }

    @Override
    public List<SavedCounselorResponseDTO> getSavedCounselorProfilesByUserId(Integer userId) {
        // 1) Repository를 호출하여 DB에서 필요한 데이터 조회
//        List<Object[]> results= savedCounselorRepository.findSavedCounselorsAndAvgRatingByUserId(userId);
        List<SavedCounselor> savedCounselors = savedCounselorRepository.findByUserUserId(userId);

        // results는 Object[] 배열을 담은 리스트로, 각각의 Object[]에는 [0]번 인덱스에 CounselorProfile 엔티티,
        // [1]번 인덱스에 평균 평점(Double 타입) 값이 들어있다고 가정한 쿼리입니다.

        // 2) 조회 결과를 Stream API를 이용해 DTO로 변환
        return savedCounselors.stream().map(sc -> {
            CounselorProfile cp = sc.getCounselorProfile();
            Double avgRating = cp.getReservations().stream()
                    .flatMap(res -> res.getReview() != null ? Stream.of(res.getReview().getRating()) : Stream.empty())
                    .mapToDouble(rating -> rating )
                    .average()
                    .orElse(0.0);

            // badges 리스트 추출
            List<String> badges = cp.getBadges().stream()
                    .map(cb -> cb.getBadge().getBadgeName())
                    .collect(Collectors.toList());

            // CounselorProfile에서 User 엔티티를 가져와 nickname, profileUrl 추출
            String nickname = cp.getUser().getNickname();
            String profileUrl = cp.getUser().getProfileUrl();

            // CounselorProfile의 introduction 필드 추출
            String introduction = cp.getIntroduction();

            // DTO 객체 생성
            return new SavedCounselorResponseDTO(
                    nickname,
                    profileUrl,
                    avgRating,
                    badges,
                    introduction
            );
        }).collect(Collectors.toList());
    }


    @Override
    public List<CounselorReservationDTO> getCounselorReservations(Integer userId){

        // 상담 중인 예약 목록 조회
        List<CounselingReservation> reservations = counselingReservationRepository
                .findByUser_UserIdAndStatusIn(userId, List.of(ReservationStatus.PENDING, ReservationStatus.SCHEDULED, ReservationStatus.COMPLETED));

        return reservations.stream()
                .map( reservation -> {
                    CounselorProfile counselor = reservation.getCounselorProfile();
                    return new CounselorReservationDTO(
                            counselor.getUser().getNickname(),
                            counselor.getUser().getProfileUrl(),
                            counselor.getIntroduction(),
                            counselor.getSpecialties(),
                            reservation.getAppointmentDate(),
                            reservation.getAppointmentTime(),
                            reservation.getStatus().name()
                    );
                })
                .collect(Collectors.toList());

    }
    @Override
    public UserActivityDTO getUserActivity(Integer userId) {

        return null;
    }

    @Override
    public UpdateProfileResponseDTO updateProfileWithUrl(
            Integer userId, String fileUrl, UpdateProfileRequestDTO updateProfileRequestDTO) {
        // 1. 유저 조회
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. URL 설정
        // TODO : URL 검증 하는 로직 필요.
        if (fileUrl != null && !fileUrl.isEmpty()) {
            user.setProfileUrl(fileUrl);
        }

        // 3. 닉네임 업데이트
        user.setNickname(updateProfileRequestDTO.getNickname());
        userRepository.save(user);

        // 4. 응답 DTO 반환
        return new UpdateProfileResponseDTO(user.getUserId(), user.getProfileUrl(), user.getNickname());
    }

    private String uploadFileToS3(MultipartFile file) {
        // 파일을 S3에 업로드하고 URL을 반환하는 로직을 작성해야 합니다.
        // 여기에 실제 S3 업로드 코드를 작성하세요.
//        "https://s3.amazonaws.com/yourbucket/" + file.getOriginalFilename();

        return null;

    }
}
