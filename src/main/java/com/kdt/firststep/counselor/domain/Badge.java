package com.kdt.firststep.counselor.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "badges")
public class Badge { // 인증 배지 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Integer badgeId;

    @Column(name = "badge_name", nullable = false)
    private String badgeName;

    @Column(name = "badge_description", nullable = false)
    private String description; // 배지 설명

    @Column(name = "badge_criteria", nullable = false)
    private String criteria; // 획득 조건

    @OneToMany(mappedBy = "badge")
    private List<CounselorBadge> counselorBadges = new ArrayList<>();

    @Builder
    public Badge(String badgeName, String description, String criteria) {
        this.badgeName = badgeName;
        this.description = description;
        this.criteria = criteria;
    }
}
