package com.kdt.firststep.user.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Top5CounselorResponseDTO {

    private Integer counselorId;
    private String name;
    private Double averageRating;
}
