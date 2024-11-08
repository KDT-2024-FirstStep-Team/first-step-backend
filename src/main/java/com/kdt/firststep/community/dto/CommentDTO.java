package com.kdt.firststep.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer commentId;
    private Integer userId;
    private Integer postId;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private List<ReplyDTO> replies;

}
