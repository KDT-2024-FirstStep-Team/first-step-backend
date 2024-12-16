package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.PersonalityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalityQuestionRepository extends JpaRepository<PersonalityQuestion, Integer> {

    // 활성화된 모든 질문 조회
    List<PersonalityQuestion> findAllByActiveTrue();

    // 특정 유형의 활성화된 질문 조회
    List<PersonalityQuestion> findAllByQuestionTypeAndActiveTrue(String questionType);

}
