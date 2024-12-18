package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.CounselingReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselingReviewRepository extends JpaRepository<CounselingReview, Integer> {
    // 상담사의 리뷰 목록 조회 (최신순)
    @Query("SELECT r FROM CounselingReview r " +
            "WHERE r.reservation.counselorProfile.counselorId = :counselorId " +
            "ORDER BY r.createdAt DESC")
    List<CounselingReview> findAllByCounselorIdOrderByCreatedAtDesc(@Param("counselorId") Integer counselorId);
}
