package com.kdt.firststep.counselor.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "personality_statistics")
@IdClass(PersonalityStatisticId.class)  // 복합 키 클래스
public class PersonalityStatistic {

    @Id
    @Column(name = "question_type", nullable = false)
    private Integer questionType; // 1: 감정표현, 2: 갈등해결, ...

    @Id
    @Column(name = "total_score", nullable = false)
    private Integer totalScore; // 유형 점수 (2 ~ 6)

    @Column(name = "type_description", nullable = false, columnDefinition = "TEXT")
    private String typeDescription; // 타입 설명

    @Builder
    private PersonalityStatistic(Integer questionType, Integer totalScore, String typeDescription) {
        this.questionType = questionType;
        this.totalScore = totalScore;
        this.typeDescription = typeDescription;
    }
}
