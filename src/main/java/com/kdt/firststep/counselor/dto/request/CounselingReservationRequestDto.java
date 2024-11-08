package com.kdt.firststep.counselor.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class CounselingReservationRequestDto {
    private Integer userId;              // 예약자 ID
    private Integer counselorId;         // 상담사 ID
    private LocalDate appointmentDate;  // 상담 날짜
    private LocalTime appointmentTime;  // 상담 시간
}