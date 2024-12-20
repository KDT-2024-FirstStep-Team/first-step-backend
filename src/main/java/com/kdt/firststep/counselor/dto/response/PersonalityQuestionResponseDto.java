package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.PersonalityQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalityQuestionResponseDto {
    private Integer questionId;
    private String questionType;
    private String question;
    private List<PersonalityAnswerOptionResponseDto> answerOptions;

    public static PersonalityQuestionResponseDto from(PersonalityQuestion question) {
        return PersonalityQuestionResponseDto.builder()
                .questionId(question.getQuestionId())
                .questionType(question.getQuestionType())
                .question(question.getQuestion())
                .answerOptions(question.getAnswerOptions().stream()
                        .map(PersonalityAnswerOptionResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
