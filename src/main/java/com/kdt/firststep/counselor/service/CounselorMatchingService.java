package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.response.RecommendedCounselorResponseDto;

import java.util.List;

public interface CounselorMatchingService {

    // 맞춤 상담사 TOP 5 추천
    List<RecommendedCounselorResponseDto> getRecommendedCounselors(Integer userId);

}
