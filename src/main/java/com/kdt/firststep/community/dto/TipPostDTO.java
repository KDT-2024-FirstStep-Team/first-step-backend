package com.kdt.firststep.community.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Integer likes = 0;
    private Integer comments = 0;
    private List<CommentDTO> commentList;

}
