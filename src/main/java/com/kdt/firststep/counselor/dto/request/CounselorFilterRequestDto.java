package com.kdt.firststep.counselor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CounselorFilterRequestDto { // 받을 필터 조건들 (시간대, 연령대, 성별)
    private String timeSlot;
    private String ageRange;
    private Boolean gender;
}
