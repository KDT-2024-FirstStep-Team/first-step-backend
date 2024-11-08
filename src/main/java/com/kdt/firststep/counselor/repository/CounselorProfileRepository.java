package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CounselorProfileRepository extends JpaRepository<CounselorProfile, Integer> {
    // 만족도순, 평균 평점이 높은 순으로 상담사 5명 조회
    @Query("SELECT cp.user as user, AVG(cr.rating) as avgRating " +
            "FROM CounselorProfile cp " +
            "JOIN cp.reservations r " +
            "JOIN CounselingReview cr ON cr.reservation = r " +
            "WHERE cp.user.counselorCheck = true " +  // counselorCheck 로 수정
            "GROUP BY cp.user " +
            "ORDER BY avgRating DESC")
    List<Map<String, Object>> findTop5CounselorsByRating(Pageable pageable);

    // 인기순, 상담이 완료된 건수가 많은 순으로 상담사 5명 조회
    @Query("SELECT cp.user as user, COUNT(r) as completedCount, " +
            "(SELECT AVG(cr.rating) FROM CounselingReview cr WHERE cr.reservation IN " +
            "(SELECT res FROM CounselingReservation res WHERE res.counselorProfile = cp)) as avgRating " +
            "FROM CounselorProfile cp " +
            "JOIN cp.reservations r " +
            "WHERE r.status = '완료' " +
            "AND cp.user.counselorCheck = true " +  // counselorCheck 조건 추가
            "GROUP BY cp.user " +
            "ORDER BY completedCount DESC")
    List<Map<String, Object>> findTop5CounselorsByCompletedSessions(Pageable pageable);

    // 상담사 검색
    @Query("SELECT cp.user as user, " +
            "(SELECT AVG(cr.rating) FROM CounselingReview cr WHERE cr.reservation IN " +
            "(SELECT res FROM CounselingReservation res WHERE res.counselorProfile = cp)) as avgRating " +
            "FROM CounselorProfile cp " +
            "WHERE cp.user.counselorCheck = true " +
            "AND cp.user.nickname LIKE %:keyword% " +
            "ORDER BY (SELECT AVG(cr.rating) FROM CounselingReview cr WHERE cr.reservation IN " +
            "(SELECT res FROM CounselingReservation res WHERE res.counselorProfile = cp)) DESC")
    List<Map<String, Object>> findCounselorsByNicknameKeyword(@Param("keyword") String keyword);

    // 특정 상담사의 모든 배지 조회
    @Query("SELECT b.badgeName FROM CounselorBadge cb " +
            "JOIN cb.badge b " +
            "WHERE cb.counselorProfile.counselorId = :counselorId")
    List<String> findBadgesByCounselorId(Integer counselorId);

    // 상담사 프로필 존재 여부
    boolean existsByUser(Users user);

    // 평균 평점 조회
    @Query("SELECT AVG(cr.rating) FROM CounselingReservation r " +
            "JOIN CounselingReview cr ON cr.reservation = r " +
            "WHERE r.counselorProfile.counselorId = :counselorId")
    Optional<Double> findAverageRatingByCounselorId(@Param("counselorId") Integer counselorId);
}
