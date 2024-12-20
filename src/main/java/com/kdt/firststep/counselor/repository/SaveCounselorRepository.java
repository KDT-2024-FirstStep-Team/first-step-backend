package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.counselor.domain.SavedCounselor;
import com.kdt.firststep.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveCounselorRepository extends JpaRepository<SavedCounselor, Integer> {

    // 특정 유저가 특정 상담사를 찜했는지 확인
    boolean existsByUserAndCounselorProfile(Users user, CounselorProfile counselorProfile);

    // 특정 유저의 특정 상담사 찜 정보 조회
    Optional<SavedCounselor> findByUserAndCounselorProfile(Users user, CounselorProfile counselorProfile);

    // 특정 유저의 모든 찜한 상담사 목록 조회 (필요시 사용)
    List<SavedCounselor> findAllByUser(Users user);
}