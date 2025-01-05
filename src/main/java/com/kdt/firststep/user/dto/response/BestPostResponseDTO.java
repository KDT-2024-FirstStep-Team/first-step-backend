package com.kdt.firststep.user.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BestPostResponseDTO {

    private Integer postId;
    private String nickname;
    private String title;
    private String content;
    private String registerDate;
}
