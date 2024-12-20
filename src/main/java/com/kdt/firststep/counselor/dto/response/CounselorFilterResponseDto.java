package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CounselorFilterResponseDto { // 필터링된 상담사 정보
    private Integer counselorId;
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private List<String> badges;
    private String introduction;
    private Double averageRating;

    // Querydsl Projection 용 생성자
    public CounselorFilterResponseDto(
            Integer counselorId,
            Integer userId,
            String nickname,
            String profileUrl,
            String introduction) {
        this.counselorId = counselorId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.introduction = introduction;
    }

    public void updateBadges(List<String> badges) {
        this.badges = badges;
    }

    public void updateAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
