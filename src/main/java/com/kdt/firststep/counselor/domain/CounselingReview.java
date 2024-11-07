package com.kdt.firststep.counselor.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "counseling_reviews")
public class CounselingReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true)
    private CounselingReservation reservation;

    @Column(nullable = false)
    private Integer rating;

    @Column(name = "review_content")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public CounselingReview(CounselingReservation reservation, Integer rating, String content) {
        this.reservation = reservation;
        this.rating = rating;
        this.content = content;
    }

    public void update(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }
}
