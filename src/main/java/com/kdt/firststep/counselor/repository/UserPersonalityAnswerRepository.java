package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.UserPersonalityAnswer;
import com.kdt.firststep.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPersonalityAnswerRepository extends JpaRepository<UserPersonalityAnswer, Integer> {

    // 특정 사용자의 모든 답변 조회
    List<UserPersonalityAnswer> findAllByUser(Users user);

    // 특정 사용자의 특정 질문 유형에 대한 답변들 조회
    @Query("SELECT ua FROM UserPersonalityAnswer ua " +
            "WHERE ua.user = :user " +
            "AND ua.personalityQuestion.questionId IN :questionIds")
    List<UserPersonalityAnswer> findByUserAndQuestionIds(
            @Param("user") Users user,
            @Param("questionIds") List<Integer> questionIds
    );

    // 특정 사용자의 모든 답변 삭제
    void deleteAllByUser(Users user);

}
