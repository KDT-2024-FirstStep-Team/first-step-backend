package com.kdt.firststep.user.dto.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavedCounselorResponseDTO {

    private String nickname;
    private String profileUrl;
    private Double averageRating;
    private List<String> badges;
    private String introduction;

}
