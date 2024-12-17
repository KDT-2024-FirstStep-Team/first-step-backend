package com.kdt.firststep.question.domain;


import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_answers")
public class QuestionAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_question_id", nullable = false)
    private CoupleDailyQuestion coupleDailyQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private String answerComment;

    @Column(nullable = false, updatable = false)
    private Timestamp answeredAt;}
