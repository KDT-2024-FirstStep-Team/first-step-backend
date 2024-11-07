package com.kdt.firststep.counselor.domain;

import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "counselor_profiles")
public class CounselorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counselor_id")
    private Integer counselorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private Users user;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String specialties;

    @Column(nullable = false)
    private Integer consultationFee;

    @Convert(converter = AvailableDaysConverter.class)  // 커스텀 컨버터 사용
    @Column(name = "available_days", nullable = false)
    private AvailableDays availableDays = AvailableDays.WEEKDAY;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime = LocalTime.of(9, 0);

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime = LocalTime.of(18, 0);

    @OneToMany(mappedBy = "counselorProfile")
    private List<CounselorBadge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "counselorProfile")
    private List<CounselingReservation> reservations = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public CounselorProfile(Users user, String introduction, String specialties,
                            Integer consultationFee, AvailableDays availableDays,
                            LocalTime startTime, LocalTime endTime) {
        this.user = user;
        this.introduction = introduction;
        this.specialties = specialties;
        this.consultationFee = consultationFee;
        this.availableDays = availableDays;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void update(String introduction, String specialties, Integer consultationFee,
                       AvailableDays availableDays, LocalTime startTime, LocalTime endTime) {
        this.introduction = introduction;
        this.specialties = specialties;
        this.consultationFee = consultationFee;
        this.availableDays = availableDays;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
