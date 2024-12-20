package com.kdt.firststep.counselor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonalityCheckResponseDto {
    private boolean personalityCheck;    // 성향분석 완료 여부
}
