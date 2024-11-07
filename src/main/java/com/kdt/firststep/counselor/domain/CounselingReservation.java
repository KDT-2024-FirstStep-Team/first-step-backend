package com.kdt.firststep.counselor.domain;

import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "counseling_reservations")
public class CounselingReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counselor_id")
    private CounselorProfile counselorProfile;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Convert(converter = ReservationStatusConverter.class)
    @Column(nullable = false)
    private ReservationStatus status;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private CounselingReview review;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public CounselingReservation(Users user, CounselorProfile counselorProfile,
                                 LocalDate appointmentDate, LocalTime appointmentTime) {
        validateReservationTime(counselorProfile, appointmentDate, appointmentTime);
        this.user = user;
        this.counselorProfile = counselorProfile;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = ReservationStatus.PENDING; // 기본값으로 "대기중" 설정
    }

    private void validateReservationTime(CounselorProfile counselor, LocalDate date, LocalTime time) {
        if (time.isBefore(counselor.getStartTime()) || time.isAfter(counselor.getEndTime())) {
            throw new IllegalArgumentException("상담사의 상담 가능 시간이 아닙니다.");
        }

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
                break;
        }
    }

    public void updateStatus(ReservationStatus status) {
        this.status = status;
    }
}
