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
    private int postId;
    private int userId;
    private boolean category;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private int likes;
    private int comments;
    private List<CommentDTO> commentList;
}
