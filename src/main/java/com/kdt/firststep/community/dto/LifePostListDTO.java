package com.kdt.firststep.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LifePostListDTO {
    private Integer postId;
    private String userNickname;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private Integer likes;
    private Integer comments;
}
