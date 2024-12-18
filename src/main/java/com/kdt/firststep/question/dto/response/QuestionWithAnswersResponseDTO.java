package com.kdt.firststep.question.dto.response;


import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithAnswersResponseDTO {

    private Integer questionId;
    private String content;
    private String userAnswer;        // 현재 유저의 답변
    private String partnerAnswer;     // 상대방(부부 또는 커플)의 답변
    private Boolean isCompleted;      // 답변 완료 인증 여부
    private LocalDateTime answeredAt; // 답변 완료일
}