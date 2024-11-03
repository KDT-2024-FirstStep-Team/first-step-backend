package com.kdt.firststep.user.repository;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CounselorRepository extends JpaRepository<CounselorProfile, Long> {

    // CounselorProfile과 CounselingReview를 조인
    // 상담사별 평균 리뷰 점수를 계산하고 점수가 높은 순으로 상위 5명 가져옴.
    @Query("SELECT cp FROM CounselorProfile cp " +
        "JOIN CounselingReservation crv ON crv.counselor.id = cp.id " +
        "JOIN CounselingReview cr ON cr.reservation.id = crv.id " +
        "GROUP BY cp.id " +
        "ORDER BY AVG(cr.rating) DESC")
    List<CounselorProfile> findTopCounselorsByAverageRating();

}
