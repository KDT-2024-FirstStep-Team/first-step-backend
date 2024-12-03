package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.dto.response.CounselorFilterResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import static com.kdt.firststep.counselor.domain.QCounselorProfile.counselorProfile;  // 패키지명은 프로젝트에 맞게 수정

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class CounselorFilterRepositoryImpl implements CounselorFilterRepository {
    private final JPAQueryFactory queryFactory;

    private static final Logger log = LoggerFactory.getLogger(CounselorFilterRepositoryImpl.class);

    // 생성자에서 JPAQueryFactory 초기화
    public CounselorFilterRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CounselorFilterResponseDto> findCounselorsByFilters(
            LocalTime startTime, LocalTime endTime, Boolean isDawn, String ageRange, Boolean gender) {

        // 필터 조건 로깅
        log.info("Time filter - startTime: {}, endTime: {}, isDawn: {}", startTime, endTime, isDawn);
        log.info("Age Range filter: {}", ageRange);
        log.info("Gender filter: {}", gender);


        // Querydsl 을 사용한 동적 쿼리 생성
        return queryFactory
                .select(Projections.constructor(CounselorFilterResponseDto.class,
                        counselorProfile.counselorId,
                        counselorProfile.user.userId,
                        counselorProfile.user.nickname,
                        counselorProfile.user.profileUrl,
                        counselorProfile.introduction))
                .from(counselorProfile)
                .where(
                        timeFilter(startTime, endTime, isDawn),    // 시간대 필터
                        ageRangeFilter(ageRange),          // 연령대 필터
                        genderFilter(gender),              // 성별 필터
                        counselorProfile.user.counselorCheck.isTrue()  // 상담사 인증 여부 체크
                )
                .fetch();
    }

    // 시간대 필터 조건 생성
    private BooleanExpression timeFilter(LocalTime startTime, LocalTime endTime, Boolean isDawn) {
        if (startTime == null || endTime == null) return null;

        log.info("Applying time filter: {} ~ {}, isDawn: {}", startTime, endTime, isDawn);

        // 상담사의 상담 가능 시간이 요청된 시간대와 겹치는지 확인
        if (Boolean.TRUE.equals(isDawn)) {  // null-safe 체크
            // 새벽 시간대는 시작시간이나 종료시간이 0시-5시 사이에 있는 경우
            return counselorProfile.startTime.between(startTime, endTime)
                    .or(counselorProfile.endTime.between(startTime, endTime))
                    .or(counselorProfile.startTime.loe(startTime)
                            .and(counselorProfile.endTime.goe(endTime)));
        } else {
            // 다른 시간대는 일반적인 AND 조건으로 처리
            return counselorProfile.startTime.loe(endTime)
                    .and(counselorProfile.endTime.goe(startTime));
        }
    }

    // 연령대 필터 조건 생성
    private BooleanExpression ageRangeFilter(String ageRange) {
        if (ageRange == null) return null;

        log.info("Applying age range filter: {}", ageRange);

        LocalDate now = LocalDate.now();
        int startAge = getAgeRangeStart(ageRange);
        int endAge = getAgeRangeEnd(ageRange);

        // 생일을 고려한 계산
        LocalDate startBirth = now.minusYears(endAge + 1).plusDays(1);
        LocalDate endBirth = now.minusYears(startAge).plusDays(1);

        return counselorProfile.user.birth.between(startBirth, endBirth);
    }

    // 성별 필터 조건 생성
    private BooleanExpression genderFilter(Boolean gender) {
        if (gender == null) return null;
        log.info("Applying gender filter: {}", gender);
        return counselorProfile.user.gender.eq(gender);
    }

    // 연령대 시작 나이 계산 (예: "20대" -> 20)
    private int getAgeRangeStart(String ageRange) {
        return Integer.parseInt(ageRange.replace("대", ""));
    }

    // 연령대 종료 나이 계산 (예: "20대" -> 29)
    private int getAgeRangeEnd(String ageRange) {
        return Integer.parseInt(ageRange.replace("대", "")) + 9;
    }
}
