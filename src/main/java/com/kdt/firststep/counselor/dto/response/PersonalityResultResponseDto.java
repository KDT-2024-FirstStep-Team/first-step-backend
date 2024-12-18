package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.UserPersonalityType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@Builder
public class PersonalityResultResponseDto {
    // 감정표현 영역
    private String communicationType;
    private Integer communicationScore;
    private String communicationDescription;

    // 갈등해결 영역
    private String conflictType;
    private Integer conflictScore;
    private String conflictDescription;

    // 재정관리 영역
    private String financialType;
    private Integer financialScore;
    private String financialDescription;

    // 여가생활 영역
    private String stressType;
    private Integer stressScore;
    private String stressDescription;

    // 가치관 영역
    private String valueType;
    private Integer valueScore;
    private String valueDescription;

    // 분석 시간
    private LocalDateTime analyzedAt;

    private PersonalityResultResponseDto(
            String communicationType, Integer communicationScore, String communicationDescription,
            String conflictType, Integer conflictScore, String conflictDescription,
            String financialType, Integer financialScore, String financialDescription,
            String stressType, Integer stressScore, String stressDescription,
            String valueType, Integer valueScore, String valueDescription,
            LocalDateTime analyzedAt) {
        this.communicationType = communicationType;
        this.communicationScore = communicationScore;
        this.communicationDescription = communicationDescription;
        this.conflictType = conflictType;
        this.conflictScore = conflictScore;
        this.conflictDescription = conflictDescription;
        this.financialType = financialType;
        this.financialScore = financialScore;
        this.financialDescription = financialDescription;
        this.stressType = stressType;
        this.stressScore = stressScore;
        this.stressDescription = stressDescription;
        this.valueType = valueType;
        this.valueScore = valueScore;
        this.valueDescription = valueDescription;
        this.analyzedAt = analyzedAt;
    }

    public static PersonalityResultResponseDto from(UserPersonalityType result, Map<Integer, String> descriptions) {
        return PersonalityResultResponseDto.builder()
                .communicationType("감정표현")
                .communicationScore(result.getCommunicationSkill())
                .communicationDescription(descriptions.get(1))
                .conflictType("갈등해결")
                .conflictScore(result.getConflictManagement())
                .conflictDescription(descriptions.get(2))
                .financialType("재정관리")
                .financialScore(result.getFinancialManagement())
                .financialDescription(descriptions.get(3))
                .stressType("여가생활")
                .stressScore(result.getStressManagement())
                .stressDescription(descriptions.get(4))
                .valueType("가치관")
                .valueScore(result.getPersonalValues())
                .valueDescription(descriptions.get(5))
                .analyzedAt(result.getAnalyzedAt())
                .build();
    }
}
