package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.CounselingReservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class CounselingReservationListResponseDto {
    private Long reservationId;           // 예약 ID
    private String counselorNickname;     // 상담사 닉네임
    private String counselorProfileUrl;   // 상담사 프로필 이미지
    private LocalDate appointmentDate;    // 상담 날짜
    private LocalTime appointmentTime;    // 상담 시작 시간
    private String status;                // 예약 상태 (한글)

    public static CounselingReservationListResponseDto from(CounselingReservation reservation) {
        return CounselingReservationListResponseDto.builder()
                .reservationId(reservation.getReservationId())
                .counselorNickname(reservation.getCounselorProfile().getUser().getNickname())
                .counselorProfileUrl(reservation.getCounselorProfile().getUser().getProfileUrl())
                .appointmentDate(reservation.getAppointmentDate())
                .appointmentTime(reservation.getAppointmentTime())
                .status(reservation.getStatus().getStatus())  // Enum 의 한글값
                .build();
    }
}