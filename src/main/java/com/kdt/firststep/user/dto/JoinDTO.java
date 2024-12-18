package com.kdt.firststep.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {
    private String username;

    private String nickname;

    private String email;

    private String password;

    private LocalDate birth;

    private boolean gender;

    private String phoneNumber;
}
