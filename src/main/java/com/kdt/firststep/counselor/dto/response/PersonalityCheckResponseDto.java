package com.kdt.firststep.counselor.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonalityCheckResponseDto {
    private boolean personalityCheck;    // 성향분석 완료 여부

    // 정적 팩토리 메서드 패턴을 사용하여 DTO 객체 생성
    public static PersonalityCheckResponseDto from(boolean isChecked) {
        return PersonalityCheckResponseDto.builder()
                .personalityCheck(isChecked)
                .build();
    }
}
