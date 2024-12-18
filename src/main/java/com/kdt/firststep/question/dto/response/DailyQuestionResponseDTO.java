package com.kdt.firststep.question.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyQuestionResponseDTO {

        private Integer questionId;
        private String content;
        private LocalDateTime createdAt;
        private Boolean active;
}
