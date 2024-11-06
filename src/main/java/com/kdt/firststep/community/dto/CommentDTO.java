package com.kdt.firststep.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private int commentId;
    private int userId;
    private int postId;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
}
