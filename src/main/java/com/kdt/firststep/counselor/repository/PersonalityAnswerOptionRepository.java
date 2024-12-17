package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.PersonalityAnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalityAnswerOptionRepository extends JpaRepository<PersonalityAnswerOption, Integer> {

    // 특정 질문의 답변 옵션들 조회
    List<PersonalityAnswerOption> findAllByPersonalityQuestionQuestionId(Integer questionId);

    // 여러 질문의 답변 옵션들 조회
    List<PersonalityAnswerOption> findAllByPersonalityQuestionQuestionIdIn(List<Integer> questionIds);

}
