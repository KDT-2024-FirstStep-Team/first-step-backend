package com.kdt.firststep.user.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounselorReservationDTO {
    private String counselorNickname;
    private String counselorProfileUrl;
    private String introduction;
    private String specialties;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
}
