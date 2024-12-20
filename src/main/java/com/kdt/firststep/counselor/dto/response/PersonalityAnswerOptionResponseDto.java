package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.PersonalityAnswerOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalityAnswerOptionResponseDto {
    private Integer optionId;
    private String optionText;
    private Integer score;

    public static PersonalityAnswerOptionResponseDto from(PersonalityAnswerOption option) {
        return PersonalityAnswerOptionResponseDto.builder()
                .optionId(option.getOptionId())
                .optionText(option.getOptionText())
                .score(option.getScore())
                .build();
    }
}
