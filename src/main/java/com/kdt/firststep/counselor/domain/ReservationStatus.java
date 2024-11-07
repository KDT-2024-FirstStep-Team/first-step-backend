package com.kdt.firststep.counselor.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus { // 예약 상태
    PENDING("대기중"), // 예약 신청 상태
    SCHEDULED("예약확정"), // 상담사가 예약을 승인
    COMPLETED("완료"), // 상담 완료
    CANCELLED("취소"); // 상담 취소

    private final String status; // status 필드로 한글 상태값을 저장하여 화면에 표시할 때 사용

    // DB에 저장될 때는 value 값(한글)으로 저장됨
    @Override
    public String toString() {
        return status;
    }

}
