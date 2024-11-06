package com.kdt.firststep.user.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounselorContentSummaryResponseDTO {

    private Integer postId;
    private String title;
    private String writerName;
    private Integer likes;
}
