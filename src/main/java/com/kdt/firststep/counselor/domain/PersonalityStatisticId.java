package com.kdt.firststep.counselor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityStatisticId implements Serializable {
    private Integer questionType;
    private Integer totalScore;
}
