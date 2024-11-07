package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CounselorTopResponseDto {
    private String nickname;        // 상담사 닉네임
    private double averageRating;   // 평균 평점
    private List<String> badges;    // 보유한 배지들
    private String introduction;    // 한 줄 소개
    private String profileUrl;      // 프로필 이미지 URL

    public static CounselorTopResponseDto of(User user, double avgRating, List<String> badges) {
        return CounselorTopResponseDto.builder()
                .nickname(user.getNickname())
                .averageRating(Math.round(avgRating * 10.0) / 10.0)  // 소수점 첫째자리까지 반올림
                .badges(badges)
                .introduction(user.getCounselorProfile().getIntroduction())
                .profileUrl(user.getProfileUrl())
                .build();
    }
}
