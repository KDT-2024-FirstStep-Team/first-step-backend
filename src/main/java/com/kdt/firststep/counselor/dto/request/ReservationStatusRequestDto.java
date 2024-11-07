package com.kdt.firststep.counselor.dto.request;

import com.kdt.firststep.counselor.domain.ReservationStatus;
import lombok.Getter;

@Getter
public class ReservationStatusRequestDto {
    private ReservationStatus status;  // 변경할 상태
}
