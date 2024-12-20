package com.kdt.firststep.counselor.dto.response;

import com.kdt.firststep.counselor.domain.CounselingReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewListResponseDto {
    private Integer reviewId;
    private Integer rating;
    private String content;
    private String userNickname;
    private String userProfileUrl;
    private LocalDateTime createdAt;

    public static ReviewListResponseDto from(CounselingReview review) {
        return ReviewListResponseDto.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .content(review.getContent())
                .userNickname(review.getReservation().getUser().getNickname())
                .userProfileUrl(review.getReservation().getUser().getProfileUrl())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
