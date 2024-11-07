package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.CounselingReservation;
import com.kdt.firststep.counselor.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CounselingReservationRepository extends JpaRepository<CounselingReservation, Integer> {
    // 특정 시간에 예약이 있는지 확인
    boolean existsByCounselorProfileCounselorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
            Integer counselorId,
            LocalDate date,
            LocalTime time,
            ReservationStatus status
    );

    // 사용자의 예약 목록 조회 (취소 제외, 날짜 내림차순)
    @Query("SELECT r FROM CounselingReservation r " +
            "WHERE r.user.userId = :userId " +
            "AND r.status != :cancelledStatus " +
            "ORDER BY r.appointmentDate DESC, r.appointmentTime DESC")
    List<CounselingReservation> findAllByUserIdAndStatusNotOrderByDesc(
            @Param("userId") Integer userId,
            @Param("cancelledStatus") ReservationStatus cancelledStatus);

    // 확정 상태이고 상담 종료 시간이 현재보다 이전인 예약들 조회
    @Query(value = "SELECT * FROM counseling_reservations cr " +
            "WHERE cr.status = :status " +
            "AND TIMESTAMP(cr.appointment_date, cr.appointment_time) + INTERVAL 1 HOUR < :now",
            nativeQuery = true)
    List<CounselingReservation> findExpiredConfirmedReservations(
            @Param("status") String status,
            @Param("now") LocalDateTime now
    );
}
