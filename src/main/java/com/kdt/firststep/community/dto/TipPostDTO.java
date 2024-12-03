package com.kdt.firststep.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipPostDTO {
    private Integer postId;
    private Integer userId;
    private boolean category;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private Integer likes;
    private Integer comments;
    private List<CommentDTO> commentList;
}
