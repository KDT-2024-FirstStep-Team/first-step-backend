package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CounselorProfileRepository extends JpaRepository<CounselorProfile, Long> {
    // 평균 평점이 높은 순으로 상담사 5명 조회
    @Query("SELECT cp.user as user, AVG(cr.rating) as avgRating " +
            "FROM CounselorProfile cp " +
            "JOIN cp.reservations r " +
            "JOIN CounselingReview cr ON cr.reservation = r " +
            "WHERE cp.user.counselorCheck = true " +  // counselorCheck 로 수정
            "GROUP BY cp.user " +
            "ORDER BY avgRating DESC")
    List<Map<String, Object>> findTop5CounselorsByRating(Pageable pageable);

    // 상담이 완료된 건수가 많은 순으로 상담사 5명 조회
    @Query("SELECT cp.user as user, COUNT(r) as completedCount " +
            "FROM CounselorProfile cp " +
            "JOIN cp.reservations r " +
            "WHERE r.status = 'COMPLETED' " +
            "GROUP BY cp.user " +
            "ORDER BY completedCount DESC")
    List<Map<String, Object>> findTop5CounselorsByCompletedSessions(Pageable pageable);

    // 상담사 검색
    @Query("SELECT cp.user as user " +
            "FROM CounselorProfile cp " +
            "WHERE cp.user.counselorCheck = true " +
            "AND cp.user.nickname LIKE %:keyword%")
    List<User> findCounselorsByNicknameKeyword(@Param("keyword") String keyword);

    // 특정 상담사의 모든 배지 조회
    @Query("SELECT b.badgeName FROM CounselorBadge cb " +
            "JOIN cb.badge b " +
            "WHERE cb.counselorProfile.counselorId = :counselorId")
    List<String> findBadgesByCounselorId(Long counselorId);

    // 상담사 프로필 존재 여부
    boolean existsByUser(User user);

    // 평균 평점 조회
    @Query("SELECT AVG(cr.rating) FROM CounselingReservation r " +
            "JOIN CounselingReview cr ON cr.reservation = r " +
            "WHERE r.counselorProfile.counselorId = :counselorId")
    Optional<Double> findAverageRatingByCounselorId(@Param("counselorId") Long counselorId);
}
