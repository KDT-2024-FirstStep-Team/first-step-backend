package com.kdt.firststep.question.repository;

import com.kdt.firststep.question.domain.DailyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<DailyQuestion, Integer> {
}
