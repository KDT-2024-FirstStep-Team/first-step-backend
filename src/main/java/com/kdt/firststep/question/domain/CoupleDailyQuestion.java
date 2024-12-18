package com.kdt.firststep.question.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "couple_daily_question")
public class CoupleDailyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coupleQuestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Matches match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private DailyQuestion dailyQuestion;

    @Column(nullable = false)
    private LocalDate questionDate;

    @Column(nullable = false)
    private Boolean isCompleted;

    @OneToMany(mappedBy = "coupleDailyQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAnswers> questionAnswers;
}
