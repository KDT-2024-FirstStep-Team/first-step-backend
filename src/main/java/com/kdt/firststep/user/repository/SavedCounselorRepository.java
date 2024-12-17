package com.kdt.firststep.user.repository;

import com.kdt.firststep.counselor.domain.SavedCounselor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedCounselorRepository extends JpaRepository<SavedCounselor, Long> {

    List<SavedCounselor> findByUserUserId(Integer userId);

//    @Query("""
//            SELECT cp as counselorProfile, AVG(cr.rating) as avgRating
//            FROM SavedCounselor sc
//            JOIN sc.counselorProfile cp
//            JOIN cp.user u
//            LEFT JOIN cp.reservations res
//            LEFT JOIN res.review cr
//            WHERE sc.user.userId = :userId
//            GROUP BY cp
//            """)
//    List<Object[]> findSavedCounselorsAndAvgRatingByUserId(@Param("userId") Integer userId);
}
