package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.AvailableDays;
import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.counselor.dto.request.CounselorDetailRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselorDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorTopResponseDto;
import com.kdt.firststep.counselor.repository.CounselorProfileRepository;
import com.kdt.firststep.counselor.repository.UserPersonalityTypeRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CounselorServiceImpl implements CounselorService {

    private final UserRepository userRepository;
    private final CounselorProfileRepository counselorProfileRepository;

    /**
     * 만족도 순 TOP5 상담사 목록 조회 (평점 순)
     */
    @Override
    public List<CounselorTopResponseDto> getTopCounselorsByRating() {
        // 상위 5명의 상담사만 조회
        List<Map<String, Object>> topCounselors = counselorProfileRepository
                .findTop5CounselorsByRating(PageRequest.of(0, 5));

        // 조회된 상담사 정보를 DTO 로 변환
        return topCounselors.stream()
                .map(result -> {
                    Users user = (Users) result.get("user");
                    Integer counselorId = (Integer) result.get("counselorId");
                    Integer userId = user.getUserId();
                    Double avgRating = ((Number) result.get("avgRating")).doubleValue();
                    // 각 상담사의 배지 정보 조회
                    List<String> badges = counselorProfileRepository
                            .findBadgesByCounselorId(counselorId);
                    return CounselorTopResponseDto.of(user, counselorId, userId, avgRating, badges);
                })
                .collect(Collectors.toList());
    }

    /**
     * 인기 순 TOP5 상담사 목록 조회 (완료된 상담이 많은 순)
     */
    @Override
    public List<CounselorTopResponseDto> getTopCounselorsByPopularity() {
        List<Map<String, Object>> topCounselors = counselorProfileRepository
                .findTop5CounselorsByCompletedSessions(PageRequest.of(0, 5));

        return topCounselors.stream()
                .map(result -> {
                    Users user = (Users) result.get("user");
                    Integer counselorId = (Integer) result.get("counselorId");
                    Integer userId = user.getUserId();
                    Double avgRating = (result.get("avgRating") != null)
                            ? ((Number) result.get("avgRating")).doubleValue()
                            : 0.0;
                    List<String> badges = counselorProfileRepository
                            .findBadgesByCounselorId(counselorId);
                    return CounselorTopResponseDto.of(user, counselorId, userId, avgRating, badges);
                })
                .collect(Collectors.toList());
    }

    /**
     * 검색 - 닉네임과 일치하는 상담사 조회
     */
    @Override
    public List<CounselorTopResponseDto> searchCounselorsByNickname(String keyword) {
        List<Map<String, Object>> counselors = counselorProfileRepository.findCounselorsByNicknameKeyword(keyword);

        return counselors.stream()
                .map(result -> {
                    Users user = (Users) result.get("user");
                    Integer counselorId = (Integer) result.get("counselorId");
                    Integer userId = user.getUserId();
                    Double avgRating = (result.get("avgRating") != null)
                            ? ((Number) result.get("avgRating")).doubleValue()
                            : 0.0;
                    List<String> badges = counselorProfileRepository
                            .findBadgesByCounselorId(counselorId);
                    return CounselorTopResponseDto.of(user, counselorId, userId, avgRating, badges);
                })
                .collect(Collectors.toList());
    }

    /**
     * 상담사 상세 프로필 생성
     */
    @Override
    @Transactional  // 데이터 수정이 있으므로 readOnly = false
    public void createCounselorDetail(Integer userId, CounselorDetailRequestDto requestDto) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 상담사 권한 체크 추가
        if (!user.getCounselorCheck()) {
            throw new IllegalStateException("상담사 권한이 없는 사용자입니다.");
        }

        // 상담사 프로필 존재 여부 체크
        if (counselorProfileRepository.existsByUser(user)) {
            throw new IllegalStateException("이미 존재하는 상담사 프로필입니다.");
        }

        // 한글값으로 Enum 찾기 (프론트에서 한글로 보내주면 => 영어로 들어옴, DB에 저장될 때 영어 => 한글)
        AvailableDays availableDays = Arrays.stream(AvailableDays.values())
                .filter(day -> day.getDay().equals(requestDto.getAvailableDays()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요일 형식입니다."));

        CounselorProfile counselorProfile = CounselorProfile.builder()
                .user(user)
                .introduction(requestDto.getIntroduction())
                .specialties(requestDto.getSpecialties())
                .consultationFee(requestDto.getConsultationFee())
                .availableDays(availableDays)  // 찾은 Enum 값 사용
                .startTime(LocalTime.parse(requestDto.getStartTime()))
                .endTime(LocalTime.parse(requestDto.getEndTime()))
                .build();

        counselorProfileRepository.save(counselorProfile);
    }

    /**
     * 상담사 상세 프로필 조회
     */
    @Override
    public CounselorDetailResponseDto getCounselorDetail(Integer counselorId) {
        CounselorProfile counselorProfile = counselorProfileRepository.findById(counselorId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        List<String> badges = counselorProfileRepository.findBadgesByCounselorId(counselorId);
        Double averageRating = counselorProfileRepository.findAverageRatingByCounselorId(counselorId)
                .orElse(0.0);

        // 완료된 상담 건수 조회
        Integer completedSessionCount = counselorProfileRepository
                .countCompletedSessionsByCounselorId(counselorId);

        return CounselorDetailResponseDto.from(counselorProfile, badges, averageRating, completedSessionCount);
    }

    /**
     * 상담사 상세 프로필 수정
     */
    @Override
    @Transactional
    public void updateCounselorDetail(Integer counselorId, CounselorDetailRequestDto requestDto) {
        CounselorProfile counselorProfile = counselorProfileRepository.findById(counselorId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        // 한글값으로 Enum 찾기
        AvailableDays availableDays = Arrays.stream(AvailableDays.values())
                .filter(day -> day.getDay().equals(requestDto.getAvailableDays()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요일 형식입니다."));

        counselorProfile.update(
                requestDto.getIntroduction(),
                requestDto.getSpecialties(),
                requestDto.getConsultationFee(),
                availableDays,
                LocalTime.parse(requestDto.getStartTime()),
                LocalTime.parse(requestDto.getEndTime())
        );
    }
}
