package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer completedSessionCount; // 완료된 상담 건수
    private Boolean hasChildren;           // 자녀 유무 (false: 없음, true: 있음)
    private Boolean gender;                // 성별 (false: 여자, true: 남자)
    private String ageRange;               // 연령대 (20대, 30대 등)

    public static CounselorDetailResponseDto from(
            CounselorProfile counselorProfile,
            List<String> badges,
            Double averageRating,
            Integer completedSessionCount) {
        Users user = counselorProfile.getUser();

        // 연령대 계산
        int age = LocalDate.now().getYear() - user.getBirth().getYear();
        String ageRange = ((age / 10) * 10) + "대";

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
                .completedSessionCount(completedSessionCount)
                .hasChildren(user.getChildStatus())
                .gender(user.getGender())
                .ageRange(ageRange)
                .build();
    }
}
