package com.kdt.firststep.counselor.domain;

import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_personality_answers")
public class UserPersonalityAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private PersonalityQuestion personalityQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private PersonalityAnswerOption selectedOption;

    @Column(name = "plus_score", nullable = false)
    private Integer plusScore;

    @Column(name = "answered_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime answeredAt;

    @Builder
    private UserPersonalityAnswer(Users user, PersonalityQuestion personalityQuestion,
                                  PersonalityAnswerOption selectedOption, Integer plusScore) {
        this.user = user;
        this.personalityQuestion = personalityQuestion;
        this.selectedOption = selectedOption;
        this.plusScore = plusScore;
    }
}
