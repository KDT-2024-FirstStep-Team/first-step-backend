package com.kdt.firststep.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiredTime;
    private long refreshTokenExpiredTime;
}
