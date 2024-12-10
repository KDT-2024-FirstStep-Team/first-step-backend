package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;

public interface PersonalityService {

    // 성향 분석 여부 확인
    PersonalityCheckResponseDto checkPersonalityStatus(Integer userId);

}
