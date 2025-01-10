package com.kdt.firststep.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipPostListDTO {
    private Integer postId;
    private String userNickname;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private Integer likes;
    private Integer comments;
    private String previewImageUrl;
}
