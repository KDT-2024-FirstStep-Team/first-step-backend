package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.AvailableDays;
import com.kdt.firststep.counselor.domain.CounselingReservation;
import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.dto.request.CounselingReservationRequestDto;
import com.kdt.firststep.counselor.dto.request.CounselorDetailRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselingReservationListResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorTopResponseDto;
import com.kdt.firststep.counselor.repository.CounselingReservationRepository;
import com.kdt.firststep.counselor.repository.CounselorProfileRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CounselingService {
    private final UserRepository userRepository;
    private final CounselorProfileRepository counselorProfileRepository;
    private final CounselingReservationRepository counselingReservationRepository;

    public List<CounselorTopResponseDto> getTopCounselorsByRating() {
        // 상위 5명의 상담사만 조회
        List<Map<String, Object>> topCounselors = counselorProfileRepository
                .findTop5CounselorsByRating(PageRequest.of(0, 5));

        // 조회된 상담사 정보를 DTO 로 변환
        return topCounselors.stream()
                .map(result -> {
                    Users user = (Users) result.get("user");
                    Double avgRating = ((Number) result.get("avgRating")).doubleValue();
                    // 각 상담사의 배지 정보 조회
                    List<String> badges = counselorProfileRepository
                            .findBadgesByCounselorId(user.getCounselorProfile().getCounselorId());
                    return CounselorTopResponseDto.of(user, avgRating, badges);
                })
                .collect(Collectors.toList());
    }

    // 인기순(완료된 상담이 많은 순) TOP5 상담사 목록 조회
    public List<CounselorTopResponseDto> getTopCounselorsByPopularity() {
        List<Map<String, Object>> topCounselors = counselorProfileRepository
                .findTop5CounselorsByCompletedSessions(PageRequest.of(0, 5));

        return topCounselors.stream()
                .map(result -> {
                    Users user = (Users) result.get("user");
                    List<String> badges = counselorProfileRepository
                            .findBadgesByCounselorId(user.getCounselorProfile().getCounselorId());
                    return CounselorTopResponseDto.of(user, 0.0, badges);
                })
                .collect(Collectors.toList());
    }

    // 닉네임으로 상담사 검색
    public List<CounselorTopResponseDto> searchCounselorsByNickname(String keyword) {
        List<Users> counselors = counselorProfileRepository.findCounselorsByNicknameKeyword(keyword);

        return counselors.stream()
                .map(user -> {
                    List<String> badges = counselorProfileRepository
                            .findBadgesByCounselorId(user.getCounselorProfile().getCounselorId());
                    return CounselorTopResponseDto.of(user, 0.0, badges);
                })
                .collect(Collectors.toList());
    }

    // 상담사 상세 정보 생성
    @Transactional  // 데이터 수정이 있으므로 readOnly = false
    public void createCounselorDetail(Integer userId, CounselorDetailRequestDto requestDto) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        if (counselorProfileRepository.existsByUser(user)) {   // 수정된 부분
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

    // 상담사 상세 정보 조회
    public CounselorDetailResponseDto getCounselorDetail(Integer counselorId) {
        CounselorProfile counselorProfile = counselorProfileRepository.findById(counselorId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        List<String> badges = counselorProfileRepository.findBadgesByCounselorId(counselorId);
        Double averageRating = counselorProfileRepository.findAverageRatingByCounselorId(counselorId)
                .orElse(0.0);

        return CounselorDetailResponseDto.from(counselorProfile, badges, averageRating);
    }

    // 상담사 상세 정보 수정
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

    // 상담 예약
    @Transactional
    public void createReservation(CounselingReservationRequestDto requestDto) {
        // 1. 상담사 존재 여부 확인
        CounselorProfile counselor = counselorProfileRepository.findById(requestDto.getCounselorId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        // 2. 예약자 존재 여부 확인
        Users user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 3. 해당 시간에 이미 예약이 있는지 확인
        if (counselingReservationRepository.existsByCounselorProfileCounselorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
                requestDto.getCounselorId(),
                requestDto.getAppointmentDate(),
                requestDto.getAppointmentTime(),
                ReservationStatus.CANCELLED)) {
            throw new IllegalStateException("해당 시간에는 이미 예약이 존재합니다.");
        }

        // 4. 상담 가능 시간인지 확인
        validateAvailableTime(counselor, requestDto.getAppointmentDate(), requestDto.getAppointmentTime());

        // 5. 예약 생성
        CounselingReservation reservation = CounselingReservation.builder()
                .user(user)
                .counselorProfile(counselor)
                .appointmentDate(requestDto.getAppointmentDate())
                .appointmentTime(requestDto.getAppointmentTime())
                .build();

        counselingReservationRepository.save(reservation);
    }

    // 상담 가능 시간 검증 메서드
    private void validateAvailableTime(CounselorProfile counselor, LocalDate date, LocalTime time) {
        // 1. 상담 가능 시간 체크
        if (time.isBefore(counselor.getStartTime()) || time.isAfter(counselor.getEndTime())) {
            throw new IllegalArgumentException("상담사의 상담 가능 시간이 아닙니다.");
        }

        // 2. 요일 체크
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        boolean isWeekend = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;

        switch (counselor.getAvailableDays()) {
            case WEEKDAY:
                if (isWeekend) {
                    throw new IllegalArgumentException("해당 상담사는 평일에만 상담이 가능합니다.");
                }
                break;
            case WEEKEND:
                if (!isWeekend) {
                    throw new IllegalArgumentException("해당 상담사는 주말에만 상담이 가능합니다.");
                }
                break;
            case ALL:
                break; // 모든 요일 가능
        }
    }

    // 사용자의 예약 목록 조회 (취소 상태 제외)
    public List<CounselingReservationListResponseDto> getReservations(Integer userId) {
        // 유저 존재 여부 확인
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 예약 목록 조회
        List<CounselingReservation> reservations = counselingReservationRepository
                .findAllByUserIdAndStatusNotOrderByDesc(userId, ReservationStatus.CANCELLED);

        // DTO 로 변환
        return reservations.stream()
                .map(CounselingReservationListResponseDto::from)
                .collect(Collectors.toList());
    }

    // 상담 예약 상태 변경
    @Transactional
    public void updateReservationStatus(Integer reservationId, ReservationStatus newStatus) {
        // 예약 조회
        CounselingReservation reservation = counselingReservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 예약입니다."));

        // 상태 변경 가능 여부 검증
        validateStatusChange(reservation, reservation.getStatus(), newStatus);

        // 상태 변경
        reservation.updateStatus(newStatus);
    }

    // 상태 변경 가능 여부 검증
    private void validateStatusChange(CounselingReservation reservation,
                                      ReservationStatus currentStatus, ReservationStatus newStatus) {
        switch (newStatus) {
            // 1. 취소로 변경하는 경우
            case CANCELLED:
                validateCancellation(reservation, currentStatus);
                break;

            // 2. 예약확정으로 변경하는 경우
            case SCHEDULED:
                validateScheduling(currentStatus);
                break;

            // 3. 완료로 변경하는 경우 (스케줄러에서만 사용)
            case COMPLETED:
                validateCompletion(currentStatus);
                break;

            // 4. 대기중으로는 변경 불가
            case PENDING:
                throw new IllegalArgumentException("대기중 상태로 직접 변경할 수 없습니다.");
        }
    }

    // 취소 가능 여부 검증
    private void validateCancellation(CounselingReservation reservation, ReservationStatus currentStatus) {
        // 1. 상태 체크
        if (currentStatus != ReservationStatus.PENDING && currentStatus != ReservationStatus.SCHEDULED) {
            throw new IllegalStateException("취소할 수 없는 예약 상태입니다.");
        }

        // 2. 취소 가능 기간 체크 (2일 전까지만 취소 가능)
        LocalDate cancelDeadline = reservation.getAppointmentDate().minusDays(2);
        if (LocalDate.now().isAfter(cancelDeadline)) {
            throw new IllegalStateException("상담 2일 전까지만 취소가 가능합니다.");
        }
    }

    // 예약확정 가능 여부 검증
    private void validateScheduling(ReservationStatus currentStatus) {
        if (currentStatus != ReservationStatus.PENDING) {
            throw new IllegalStateException("대기중 상태의 예약만 확정할 수 있습니다.");
        }
    }

    // 완료 가능 여부 검증
    private void validateCompletion(ReservationStatus currentStatus) {
        if (currentStatus != ReservationStatus.SCHEDULED) {
            throw new IllegalStateException("예약확정 상태의 예약만 완료 처리할 수 있습니다.");
        }
    }

    @Transactional
    public void completeExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        List<CounselingReservation> expiredReservations = counselingReservationRepository
                .findExpiredConfirmedReservations(
                        ReservationStatus.SCHEDULED.name(),  // enum을 String으로 변환
                        now
                );

        for (CounselingReservation reservation : expiredReservations) {
            try {
                // validateStatusChange 메소드를 통해 상태 변경 가능 여부 검증
                validateStatusChange(reservation, reservation.getStatus(), ReservationStatus.COMPLETED);
                // 상태 변경
                reservation.updateStatus(ReservationStatus.COMPLETED);
                counselingReservationRepository.save(reservation);
                log.info("Automatically completed reservation: {}", reservation.getReservationId());
            } catch (Exception e) {
                log.error("Failed to complete reservation: {}", reservation.getReservationId(), e);
            }
        }
    }
}
