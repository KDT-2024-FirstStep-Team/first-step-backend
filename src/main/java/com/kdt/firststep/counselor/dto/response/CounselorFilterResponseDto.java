package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselorFilterResponseDto { // 필터링된 상담사 정보
    private Integer counselorId;
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private List<String> badges;
    private String introduction;
    private Double averageRating;

    // Querydsl Projection 용 생성자 추가
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

    public static CounselorFilterResponseDto of(CounselorProfile profile,
                                                List<String> badges,
                                                Double avgRating) {
        return CounselorFilterResponseDto.builder()
                .counselorId(profile.getCounselorId())
                .userId(profile.getUser().getUserId())
                .nickname(profile.getUser().getNickname())
                .profileUrl(profile.getUser().getProfileUrl())
                .badges(badges)
                .introduction(profile.getIntroduction())
                .averageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0)
                .build();
    }
}
