package com.kdt.firststep.user.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileResponseDTO {
    private Integer userId;
    private String profileUrl;  // 프로필 이미지 URL
    private String nickname;    // 닉네임
}