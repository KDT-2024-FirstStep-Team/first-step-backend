package com.kdt.firststep.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Top5CounselorResponseDTO {
        private Long counselorId;
        private String name;
        private double averageRating;

}
