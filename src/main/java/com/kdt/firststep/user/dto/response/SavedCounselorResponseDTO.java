package com.kdt.firststep.user.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavedCounselorResponseDTO {

    private Integer postId;
    private String profileUrl;
    private String nickname;
}
