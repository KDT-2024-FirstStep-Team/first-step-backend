package com.kdt.firststep.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequestDTO {
    private String file;      // 이미지 URL
    private String nickname;    // 닉네임
}
