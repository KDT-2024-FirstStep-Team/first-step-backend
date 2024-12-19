package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.user.domain.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CounselorTopResponseDto {
    private Integer counselorId;
    private Integer userId;
    private String nickname;        // 상담사 닉네임
    private Double averageRating;   // 평균 평점
    private List<String> badges;    // 보유한 배지들
    private String introduction;    // 한 줄 소개
    private String profileUrl;      // 프로필 이미지 URL

    public static CounselorTopResponseDto of(Users user, Integer counselorId, Integer userId, Double avgRating, List<String> badges) {
        return CounselorTopResponseDto.builder()
                .counselorId(counselorId)  // 추가
                .userId(userId)           // 추가
                .nickname(user.getNickname())
                .averageRating(Math.round(avgRating * 10.0) / 10.0)  // 소수점 첫째자리까지 반올림
                .badges(badges)
                .introduction(user.getCounselorProfile().getIntroduction())
                .profileUrl(user.getProfileUrl())
                .build();
    }
}
