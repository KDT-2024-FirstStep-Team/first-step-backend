package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.UserPersonalityType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityResultResponseDto {
    private List<PersonalityTypeDto> results;
    private LocalDateTime analyzedAt;

    public static PersonalityResultResponseDto from(UserPersonalityType result, Map<Integer, String> descriptions) {
        List<PersonalityTypeDto> personalityTypes = Arrays.asList(
                new PersonalityTypeDto("감정표현", result.getCommunicationSkill(), descriptions.get(1)),
                new PersonalityTypeDto("갈등해결", result.getConflictManagement(), descriptions.get(2)),
                new PersonalityTypeDto("재정관리", result.getFinancialManagement(), descriptions.get(3)),
                new PersonalityTypeDto("여가생활", result.getStressManagement(), descriptions.get(4)),
                new PersonalityTypeDto("가치관", result.getPersonalValues(), descriptions.get(5))
        );

        return new PersonalityResultResponseDto(personalityTypes, result.getAnalyzedAt());
    }
}
