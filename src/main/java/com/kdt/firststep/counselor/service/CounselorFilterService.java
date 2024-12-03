package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.request.CounselorFilterRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselorFilterResponseDto;
import com.kdt.firststep.counselor.repository.CounselorFilterRepository;
import com.kdt.firststep.counselor.repository.CounselorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CounselorFilterService {
    private final CounselorFilterRepository counselorFilterRepository;
    private final CounselorProfileRepository counselorProfileRepository;

    // 상담사 필터링 메서드
    public List<CounselorFilterResponseDto> filterCounselors(CounselorFilterRequestDto requestDto) {
        // 시간대 문자열을 LocalTime 으로 변환
        LocalTime startTime = null;
        LocalTime endTime = null;
        boolean isDawn = false;  // 새벽 시간대 여부를 체크하는 플래그 추가

        if (requestDto.getTimeSlot() != null) {
            switch (requestDto.getTimeSlot()) {
                case "아침" -> {
                    startTime = LocalTime.of(9, 0);
                    endTime = LocalTime.of(12, 0);
                }
                case "오후" -> {
                    startTime = LocalTime.of(12, 0);
                    endTime = LocalTime.of(18, 0);
                }
                case "저녁" -> {
                    startTime = LocalTime.of(18, 0);
                    endTime = LocalTime.of(23, 59);  // 24시를 23:59로 변경
                }
                case "새벽" -> {
                    startTime = LocalTime.of(0, 0);
                    endTime = LocalTime.of(5, 0);
                    isDawn = true;  // 새벽 시간대 플래그 설정
                }
            }
        }

        // 기본 필터링된 상담사 목록 조회
        List<CounselorFilterResponseDto> counselors = counselorFilterRepository
                .findCounselorsByFilters(
                        startTime,
                        endTime,
                        isDawn,
                        requestDto.getAgeRange(),
                        requestDto.getGender()
                );

        // 각 상담사별로 추가 정보(배지, 평점) 조회 및 설정
        counselors.forEach(counselor -> {
            // 배지 정보 조회
            List<String> badges = counselorProfileRepository
                    .findBadgesByCounselorId(counselor.getCounselorId());
            counselor.setBadges(badges);

            // 평균 평점 조회
            Double avgRating = counselorProfileRepository
                    .findAverageRatingByCounselorId(counselor.getCounselorId())
                    .orElse(0.0);
            counselor.setAverageRating(
                    Math.round(avgRating * 10.0) / 10.0  // 소수점 첫째자리까지 반올림
            );
        });

        return counselors;
    }
}
