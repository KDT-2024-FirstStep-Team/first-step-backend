package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class RecommendedCounselorResponseDto {
    private Integer counselorId;
    private Integer userId;
    private String nickname;
    private Double averageRating;
    private List<String> badges;
    private String introduction;
    private String profileUrl;
    private Integer matchingScore;   // 매칭 점수

    private RecommendedCounselorResponseDto(
            Integer counselorId,
            Integer userId,
            String nickname,
            Double averageRating,
            List<String> badges,
            String introduction,
            String profileUrl,
            Integer matchingScore) {
        this.counselorId = counselorId;
        this.userId = userId;
        this.nickname = nickname;
        this.averageRating = averageRating;
        this.badges = badges;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.matchingScore = matchingScore;
    }

    public static RecommendedCounselorResponseDto from(
            CounselorProfile counselor,
            Integer matchingScore,
            Double averageRating,
            List<String> badges) {
        return RecommendedCounselorResponseDto.builder()
                .counselorId(counselor.getCounselorId())
                .userId(counselor.getUser().getUserId())
                .nickname(counselor.getUser().getNickname())
                .averageRating(averageRating)
                .badges(badges)
                .introduction(counselor.getIntroduction())
                .profileUrl(counselor.getUser().getProfileUrl())
                .matchingScore(matchingScore)
                .build();
    }
}
