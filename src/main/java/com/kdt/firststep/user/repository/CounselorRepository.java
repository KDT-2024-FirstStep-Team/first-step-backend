package com.kdt.firststep.user.repository;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.user.dto.response.CounselorProfileWithRatingResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CounselorRepository extends JpaRepository<CounselorProfile, Long> {

    // CounselorProfile과 CounselingReview를 조인
    // 상담사별 평균 리뷰 점수를 계산하고 점수가 높은 순으로 상위 5명 가져옴.
    @Query("SELECT new com.kdt.firststep.user.dto.response.CounselorProfileWithRatingResponseDTO(" +
        "cp.counselorId, cp.user.userId, cp.introduction, cp.specialties, cp.consultationFee, " +
        "cp.availableDays, AVG(cr.rating)) " +
        "FROM CounselorProfile cp " +
        "JOIN CounselingReservation crv ON crv.counselorProfile = cp " +
        "JOIN CounselingReview cr ON cr.reservation = crv " +
        "GROUP BY cp.counselorId " +
        "ORDER BY AVG(cr.rating) DESC")
    List<CounselorProfileWithRatingResponseDTO> findTopCounselorsByAverageRating();


}
