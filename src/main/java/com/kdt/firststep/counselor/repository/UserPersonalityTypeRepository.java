package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.UserPersonalityType;
import com.kdt.firststep.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPersonalityTypeRepository extends JpaRepository<UserPersonalityType, Integer> {

    // 특정 사용자의 성향 분석 결과 조회
    Optional<UserPersonalityType> findByUser(Users user);

    // 기존 분석 결과 삭제 (재검사시)
    void deleteByUser(Users user);

}
