package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.PersonalityStatistic;
import com.kdt.firststep.counselor.domain.PersonalityStatisticId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalityStatisticRepository extends JpaRepository<PersonalityStatistic, PersonalityStatisticId> {

    // 각 유형과 점수에 맞는 설명 조회
    Optional<PersonalityStatistic> findByQuestionTypeAndTotalScore(Integer questionType, Integer totalScore);

}
