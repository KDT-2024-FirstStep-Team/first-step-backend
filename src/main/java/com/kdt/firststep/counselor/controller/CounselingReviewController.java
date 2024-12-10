package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.dto.request.ReviewCreateRequestDto;
import com.kdt.firststep.counselor.dto.request.ReviewUpdateRequestDto;
import com.kdt.firststep.counselor.dto.response.ReviewDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.ReviewListResponseDto;
import com.kdt.firststep.counselor.service.CounselingReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counseling/review")
public class CounselingReviewController {

    private final CounselingReviewService counselingReviewService;

    /**
     * 상담 리뷰 작성
     * @param reservationId
     * @param requestDto
     */
    @PostMapping("/{reservationId}")
    public ResponseEntity<Void> createReview(
            @PathVariable Integer reservationId,
            @RequestBody ReviewCreateRequestDto requestDto) {
        counselingReviewService.createReview(reservationId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 상담 리뷰 수정
     * @param reviewId
     * @param requestDto
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @PathVariable Integer reviewId,
            @RequestBody ReviewUpdateRequestDto requestDto) {
        counselingReviewService.updateReview(reviewId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 상담 리뷰 삭제
     * @param reviewId
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {
        counselingReviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    /**
     * 상담사별 리뷰 목록 조회
     * @param counselorId
     */
    @GetMapping("/counselor/{counselorId}")
    public ResponseEntity<List<ReviewListResponseDto>> getCounselorReviews(
            @PathVariable Integer counselorId) {
        return ResponseEntity.ok(counselingReviewService.getCounselorReviews(counselorId));
    }

    /**
     * 리뷰 상세 조회
     * @param reviewId
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> getReviewDetail(
            @PathVariable Integer reviewId) {
        return ResponseEntity.ok(counselingReviewService.getReviewDetail(reviewId));
    }

}
