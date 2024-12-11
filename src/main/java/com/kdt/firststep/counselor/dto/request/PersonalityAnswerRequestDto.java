package com.kdt.firststep.counselor.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class PersonalityAnswerRequestDto {
    private Integer userId;
    private List<PersonalityAnswerDetail> answers;

    @Getter
    @NoArgsConstructor
    @Builder
    public static class PersonalityAnswerDetail {
        private Integer questionId;
        private Integer optionId;

        private PersonalityAnswerDetail(Integer questionId, Integer optionId) {
            this.questionId = questionId;
            this.optionId = optionId;
        }
    }

    private PersonalityAnswerRequestDto(Integer userId, List<PersonalityAnswerDetail> answers) {
        this.userId = userId;
        this.answers = answers;
    }
}
