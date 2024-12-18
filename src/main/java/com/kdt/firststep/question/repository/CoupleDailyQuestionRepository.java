package com.kdt.firststep.question.repository;

import com.kdt.firststep.question.domain.CoupleDailyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoupleDailyQuestionRepository extends JpaRepository<CoupleDailyQuestion, Integer> {
    Optional<CoupleDailyQuestion> findByDailyQuestion_QuestionIdAndIsCompletedFalse(Integer questionId);

    // 특정 질문에 대한 답변 조회
    List<CoupleDailyQuestion> findByDailyQuestion_QuestionId(Integer coupleQuestionId);

}
