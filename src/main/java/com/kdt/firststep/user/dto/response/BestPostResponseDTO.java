package com.kdt.firststep.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BestPostResponseDTO {

    private Long postId;
    private String title;
    private String content;
}
