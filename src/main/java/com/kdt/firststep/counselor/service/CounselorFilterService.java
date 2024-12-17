package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.request.CounselorFilterRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselorFilterResponseDto;

import java.time.LocalTime;
import java.util.List;

public interface CounselorFilterService {

    // 필터별 상담사 조회(상담 가능 시간대, 연령대, 성별)
    List<CounselorFilterResponseDto> filterCounselors(CounselorFilterRequestDto requestDto);

}
