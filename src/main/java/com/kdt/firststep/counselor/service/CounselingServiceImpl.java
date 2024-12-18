package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.CounselingReservation;
import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.dto.request.CounselingReservationRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselingReservationListResponseDto;
import com.kdt.firststep.counselor.repository.CounselingReservationRepository;
import com.kdt.firststep.counselor.repository.CounselorProfileRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CounselingServiceImpl implements CounselingService {
    private final UserRepository userRepository;
    private final CounselorProfileRepository counselorProfileRepository;
    private final CounselingReservationRepository counselingReservationRepository;

    /**
     * 상담 예약 생성
     */
    @Override
    @Transactional
    public void createReservation(CounselingReservationRequestDto requestDto) {
        // 1. 상담사 존재 여부 확인
        CounselorProfile counselor = counselorProfileRepository.findById(requestDto.getCounselorId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        // 2. 예약자 존재 여부 확인
        Users user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 3. 해당 시간에 이미 예약이 있는지 확인
        if (counselingReservationRepository.existsByCounselorProfileCounselorIdAndAppointmentDateAndAppointmentTimeAndStatusIn(
                requestDto.getCounselorId(),
                requestDto.getAppointmentDate(),
                requestDto.getAppointmentTimeAsLocalTime(),
                Arrays.asList(ReservationStatus.PENDING, ReservationStatus.SCHEDULED))) {
            throw new IllegalStateException("해당 시간에는 이미 예약이 존재합니다.");
        }

        // 4. 상담 가능 시간인지 확인
        validateAvailableTime(counselor, requestDto.getAppointmentDate(), requestDto.getAppointmentTimeAsLocalTime());

        // 5. 예약 생성
        CounselingReservation reservation = CounselingReservation.builder()
                .user(user)
                .counselorProfile(counselor)
                .appointmentDate(requestDto.getAppointmentDate())
                .appointmentTime(requestDto.getAppointmentTimeAsLocalTime())
                .build();

        counselingReservationRepository.save(reservation);
    }

    /**
     * 사용자 상담 예약 정보 조회
     */
    @Override
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

    /**
     * 상담 예약 상태 변경 (상담사 예약 승인,사용자 예약 취소)
     */
    @Override
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

    /**
     * 만료된 상담 예약 자동 완료 처리 (백엔드 처리)
     */
    @Override
    @Transactional
    public void completeExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();

        // 현재 시점 기준, SCHEDULED 상태인 예약들 중에서 만료된 예약 조회
        List<CounselingReservation> expiredReservations = counselingReservationRepository
                .findExpiredConfirmedReservations(
                        ReservationStatus.SCHEDULED.getStatus(),
                        now
                );

        // 개별 예약 처리
        for (CounselingReservation reservation : expiredReservations) {
            try {
                // validateStatusChange 메소드를 통해 상태 변경 가능 여부 검증
                validateStatusChange(reservation, reservation.getStatus(), ReservationStatus.COMPLETED);
                // 상태 변경
                reservation.updateStatus(ReservationStatus.COMPLETED);
                counselingReservationRepository.save(reservation);
                log.info("완료된 상담 자동으로 완료 처리 : {}", reservation.getReservationId());
            } catch (Exception e) {
                log.error("예약 완료 실패: {}", reservation.getReservationId(), e);
            }
        }
    }

    /**
     * 상담사 날짜별 상담 예약 가능 시간 조회
     */
    @Override
    public List<String> getAvailableTimes(Integer counselorId, LocalDate date) {
        // 상담사 프로필 조회
        CounselorProfile counselor = counselorProfileRepository.findById(counselorId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        // 주말 여부 확인
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        boolean isWeekend = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;

        // 상담사의 가능 요일과 선택된 날짜 비교
        String availableDays = counselor.getAvailableDays().getDay(); // "평일", "주말", "모든요일" 중 하나
        if ((availableDays.equals("평일") && isWeekend) ||
                (availableDays.equals("주말") && !isWeekend)) {
            return Collections.emptyList();
        }

        // 해당 날짜의 예약된 시간 조회 (대기중, 예약확정 상태)
        List<LocalTime> reservedTimes = counselingReservationRepository
                .findReservedTimesByDateAndCounselorAndStatus(
                        date,
                        counselorId,
                        Arrays.asList(ReservationStatus.PENDING, ReservationStatus.SCHEDULED)
                );

        // 가능한 시간대 계산
        List<String> availableTimes = new ArrayList<>();
        LocalTime time = counselor.getStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (!time.isAfter(counselor.getEndTime().minusHours(1))) { // 상담은 1시간 단위
            if (!reservedTimes.contains(time)) {
                availableTimes.add(time.format(formatter));
            }
            time = time.plusHours(1);
        }


        return availableTimes;
    }


    /**
     * 상담 가능 시간 검증
     */
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

    /**
     * 상태 변경 가능 여부 검증
     */
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

    /**
     * 취소 가능 여부 검증
     */
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

    /**
     * 예약확정 가능 여부 검증
     */
    private void validateScheduling(ReservationStatus currentStatus) {
        if (currentStatus != ReservationStatus.PENDING) {
            throw new IllegalStateException("대기중 상태의 예약만 확정할 수 있습니다.");
        }
    }

    /**
     * 완료 가능 여부 검증
     */
    private void validateCompletion(ReservationStatus currentStatus) {
        if (currentStatus != ReservationStatus.SCHEDULED) {
            throw new IllegalStateException("예약확정 상태의 예약만 완료 처리할 수 있습니다.");
        }
    }
}
