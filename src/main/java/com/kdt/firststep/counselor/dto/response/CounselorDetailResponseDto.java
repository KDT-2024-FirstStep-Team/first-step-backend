package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CounselorDetailResponseDto {
    private String nickname;
    private String introduction;
    private String specialties;
    private Integer consultationFee;
    private String availableDays;
    private String startTime;
    private String endTime;
    private String profileUrl;
    private List<String> badges;
    private Double averageRating;

    public static CounselorDetailResponseDto from(
            CounselorProfile counselorProfile,
            List<String> badges,
            Double averageRating) {
        User user = counselorProfile.getUser();
        return CounselorDetailResponseDto.builder()
                .nickname(user.getNickname())
                .introduction(counselorProfile.getIntroduction())
                .specialties(counselorProfile.getSpecialties())
                .consultationFee(counselorProfile.getConsultationFee())
                .availableDays(counselorProfile.getAvailableDays().getDay())  // Enum의 한글값
                .startTime(counselorProfile.getStartTime().toString())
                .endTime(counselorProfile.getEndTime().toString())
                .profileUrl(user.getProfileUrl())
                .badges(badges)
                .averageRating(averageRating != null ? Math.round(averageRating * 10.0) / 10.0 : 0.0)
                .build();
    }
}
