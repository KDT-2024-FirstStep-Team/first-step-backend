package com.kdt.firststep.counselor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityTypeDto {
    private String type;
    private Integer score;
    private String description;
}
