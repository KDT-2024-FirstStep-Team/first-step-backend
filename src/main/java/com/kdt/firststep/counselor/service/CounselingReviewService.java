package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.request.ReviewCreateRequestDto;
import com.kdt.firststep.counselor.dto.request.ReviewUpdateRequestDto;
import com.kdt.firststep.counselor.dto.response.ReviewDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.ReviewListResponseDto;

import java.util.List;

public interface CounselingReviewService {

    // 상담 리뷰 작성
    void createReview(Integer reservationId, ReviewCreateRequestDto requestDto);

    // 상담 리뷰 수정
    void updateReview(Integer reviewId, ReviewUpdateRequestDto requestDto);

    // 상담 리뷰 삭제
    void deleteReview(Integer reviewId);

    // 상담사별 리뷰 목록 조회
    List<ReviewListResponseDto> getCounselorReviews(Integer counselorId);

    // 리뷰 상세 조회
    ReviewDetailResponseDto getReviewDetail(Integer reviewId);

}
