package com.kdt.firststep.user.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityDTO {

    private Integer postCount;
    private Integer commentCount;
    private Integer likeCount;
    private Integer saveCount;
}
