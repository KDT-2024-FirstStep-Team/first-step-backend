package com.kdt.firststep.user.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userEmail;
    private String password;
}
