package com.kdt.firststep.counselor.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class CounselingReservationRequestDto {
    private Integer userId;              // 예약자 ID
    private Integer counselorId;         // 상담사 ID
    private LocalDate appointmentDate;   // 상담 날짜
    private String appointmentTime;      // "HH:mm" 형식으로 받음

    public LocalTime getAppointmentTimeAsLocalTime() {
        return LocalTime.parse(appointmentTime);
    }
}