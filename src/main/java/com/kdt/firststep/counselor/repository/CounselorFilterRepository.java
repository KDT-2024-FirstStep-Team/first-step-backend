package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.counselor.dto.response.CounselorFilterResponseDto;

import java.time.LocalTime;
import java.util.List;

// 필터링같이 복잡한 동적 쿼리는 Querydsl, 단순 CRUD 는 Spring Data JPA 방식
public interface CounselorFilterRepository { // 상담사 필터링을 위한 커스텀 Repository 인터페이스

    // 필터 조건에 맞는 상담사 목록 조회
    List<CounselorFilterResponseDto> findCounselorsByFilters(LocalTime startTime,
                                                             LocalTime endTime,
                                                             Boolean isDawn,
                                                             String ageRange,
                                                             Boolean gender);
}
