package com.kdt.firststep.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {

    private int userId;

    private String userName;

    private String nickname;

    private String email;

    private String password;

    private LocalDate birth;

    private boolean gender;

    private String phoneNumber;

    private String profileUrl;

    private boolean modeType;

    private boolean personalityCheck;

    private boolean coupleCheck;

    private boolean counselorCheck;

    private String familyUrl;

    private int coin;

    private boolean maritalStatus;
}
