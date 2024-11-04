package com.kdt.firststep.community.dto;

import com.kdt.firststep.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int postId;
    private int userId;
    private boolean category;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private int likes = 0;
    private int comments = 0;
}
