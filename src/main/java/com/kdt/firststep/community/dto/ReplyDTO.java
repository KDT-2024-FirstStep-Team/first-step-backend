package com.kdt.firststep.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Integer replyId;
    private Integer userId;
    private Integer commentId;
    private String content;
    private LocalDateTime register_date;
    private LocalDateTime modify_date;
}
