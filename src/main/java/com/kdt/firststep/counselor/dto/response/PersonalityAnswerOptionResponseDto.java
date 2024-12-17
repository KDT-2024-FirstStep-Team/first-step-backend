package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.PersonalityAnswerOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class PersonalityAnswerOptionResponseDto {
    private Integer optionId;
    private String optionText;
    private Integer score;

    private PersonalityAnswerOptionResponseDto(Integer optionId, String optionText, Integer score) {
        this.optionId = optionId;
        this.optionText = optionText;
        this.score = score;
    }

    public static PersonalityAnswerOptionResponseDto from(PersonalityAnswerOption option) {
        return PersonalityAnswerOptionResponseDto.builder()
                .optionId(option.getOptionId())
                .optionText(option.getOptionText())
                .score(option.getScore())
                .build();
    }
}
