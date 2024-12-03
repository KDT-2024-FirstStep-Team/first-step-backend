package com.kdt.firststep.user.dto.response;

import com.kdt.firststep.counselor.domain.AvailableDays;
import com.kdt.firststep.counselor.domain.CounselorProfile;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounselorProfileWithRatingResponseDTO {

    private Integer counselorId;
    private Integer userId;
    private String introduction;
    private String specialties;
    private Integer consultationFee;
    private AvailableDays availableDays;
    private Double averageRating;
}
