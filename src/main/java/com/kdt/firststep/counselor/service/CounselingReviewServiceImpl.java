package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.CounselingReservation;
import com.kdt.firststep.counselor.domain.CounselingReview;
import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.dto.request.ReviewCreateRequestDto;
import com.kdt.firststep.counselor.dto.request.ReviewUpdateRequestDto;
import com.kdt.firststep.counselor.dto.response.ReviewDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.ReviewListResponseDto;
import com.kdt.firststep.counselor.repository.CounselingReservationRepository;
import com.kdt.firststep.counselor.repository.CounselingReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CounselingReviewServiceImpl implements CounselingReviewService {
    private final CounselingReservationRepository counselingReservationRepository;
    private final CounselingReviewRepository counselingReviewRepository;

    /**
     * 상담 리뷰 작성
     */
    @Override
    @Transactional
    public void createReview(Integer reservationId, ReviewCreateRequestDto requestDto) {
        // 1. 예약 조회
        CounselingReservation reservation = counselingReservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 예약입니다."));

        // 2. 리뷰 작성 가능 여부 검증
        validateReviewCreation(reservation);

        // 3. 리뷰 생성
        CounselingReview review = CounselingReview.builder()
                .reservation(reservation)
                .rating(requestDto.getRating())
                .content(requestDto.getContent())
                .build();

        counselingReviewRepository.save(review);
    }

    /**
     * 상담 리뷰 수정
     */
    @Override
    @Transactional
    public void updateReview(Integer reviewId, ReviewUpdateRequestDto requestDto) {
        CounselingReview review = counselingReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 리뷰입니다."));

        review.update(requestDto.getRating(), requestDto.getContent());
    }

    /**
     * 상담 리뷰 삭제
     */
    @Override
    @Transactional
    public void deleteReview(Integer reviewId) {
        CounselingReview review = counselingReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 리뷰입니다."));

        counselingReviewRepository.delete(review);
    }

    /**
     * 상담사별 리뷰 목록 조회
     */
    @Override
    public List<ReviewListResponseDto> getCounselorReviews(Integer counselorId) {
        List<CounselingReview> reviews = counselingReviewRepository.findAllByCounselorIdOrderByCreatedAtDesc(counselorId);
        return reviews.stream()
                .map(ReviewListResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 리뷰 상세 조회
     */
    @Override
    public ReviewDetailResponseDto getReviewDetail(Integer reviewId) {
        CounselingReview review = counselingReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 리뷰입니다."));
        return ReviewDetailResponseDto.from(review);
    }

    /**
     * 리뷰 작성 가능 여부 검증
     */
    private void validateReviewCreation(CounselingReservation reservation) {
        // 1. 상담이 완료된 상태인지 확인
        if (reservation.getStatus() != ReservationStatus.COMPLETED) {
            throw new IllegalStateException("완료된 상담에 대해서만 리뷰를 작성할 수 있습니다.");
        }

        // 2. 이미 리뷰가 존재하는지 확인
        if (reservation.getReview() != null) {
            throw new IllegalStateException("이미 작성된 리뷰가 존재합니다.");
        }
    }
}
