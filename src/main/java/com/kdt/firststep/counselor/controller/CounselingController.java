package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.dto.request.*;
import com.kdt.firststep.counselor.dto.response.*;
import com.kdt.firststep.counselor.repository.CounselorFilterRepositoryImpl;
import com.kdt.firststep.counselor.service.CounselingService;
import com.kdt.firststep.counselor.service.CounselorFilterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counselor")
public class CounselingController {
    private final CounselingService counselingService;
    private final CounselorFilterService counselorFilterService;

    // 만족도 TOP5 상담사 조회 API (리뷰 평점 평균이 높은 순)
    @GetMapping("/top/satisfaction")
    public ResponseEntity<List<CounselorTopResponseDto>> getTopCounselors() {
        return ResponseEntity.ok(counselingService.getTopCounselorsByRating());
    }

    // 인기순 TOP5 상담사 조회 API (완료된 상담 건수가 많은 순)
    @GetMapping("/top/popularity")
    public ResponseEntity<List<CounselorTopResponseDto>> getTopCounselorsByPopularity() {
        return ResponseEntity.ok(counselingService.getTopCounselorsByPopularity());
    }

    // 상담사 닉네임 검색 API
    @GetMapping("/search")
    public ResponseEntity<List<CounselorTopResponseDto>> searchCounselors(
            @RequestParam String keyword) {
        return ResponseEntity.ok(counselingService.searchCounselorsByNickname(keyword));
    }

    // 상담사 상세 정보 생성 API
    @PostMapping("/detail")
    public ResponseEntity<Void> createCounselorDetail(
            @RequestParam Integer userId,
            @RequestBody CounselorDetailRequestDto requestDto) {
        counselingService.createCounselorDetail(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 상담사 상세 정보 조회 API
    @GetMapping("/detail/{counselorId}")
    public ResponseEntity<CounselorDetailResponseDto> getCounselorDetail(
            @PathVariable Integer counselorId) {
        return ResponseEntity.ok(counselingService.getCounselorDetail(counselorId));
    }

    // 상담사 상세 정보 수정 API
    @PutMapping("/detail/{counselorId}")
    public ResponseEntity<Void> updateCounselorDetail(
            @PathVariable Integer counselorId,
            @RequestBody CounselorDetailRequestDto requestDto) {
        counselingService.updateCounselorDetail(counselorId, requestDto);
        return ResponseEntity.ok().build();
    }

    // 상담 예약 생성 API
    @PostMapping("/reservation")
    public ResponseEntity<Void> createReservation(@RequestBody CounselingReservationRequestDto requestDto) {
        counselingService.createReservation(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 상담 예약 목록 조회 API
    @GetMapping("/reservations")
    public ResponseEntity<List<CounselingReservationListResponseDto>> getReservations(
            @RequestParam Integer userId) {
        return ResponseEntity.ok(counselingService.getReservations(userId));
    }

    // 예약 승인 (상담사가 승인)
    @PutMapping("/reservation/{reservationId}/approve")
    public ResponseEntity<Void> approveReservation(@PathVariable Integer reservationId) {
        counselingService.updateReservationStatus(reservationId, ReservationStatus.SCHEDULED);
        return ResponseEntity.ok().build();
    }

    // 예약 취소 (사용자가 취소)
    @PutMapping("/reservation/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Integer reservationId) {
        counselingService.updateReservationStatus(reservationId, ReservationStatus.CANCELLED);
        return ResponseEntity.ok().build();
    }

    // 리뷰 작성
    @PostMapping("/review/{reservationId}")
    public ResponseEntity<Void> createReview(
            @PathVariable Integer reservationId,
            @RequestBody ReviewCreateRequestDto requestDto) {
        counselingService.createReview(reservationId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 리뷰 수정
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @PathVariable Integer reviewId,
            @RequestBody ReviewUpdateRequestDto requestDto) {
        counselingService.updateReview(reviewId, requestDto);
        return ResponseEntity.ok().build();
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {
        counselingService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    // 상담사의 리뷰 목록 조회
    @GetMapping("/review/counselor/{counselorId}")
    public ResponseEntity<List<ReviewListResponseDto>> getCounselorReviews(
            @PathVariable Integer counselorId) {
        return ResponseEntity.ok(counselingService.getCounselorReviews(counselorId));
    }

    // 리뷰 상세 조회
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> getReviewDetail(
            @PathVariable Integer reviewId) {
        return ResponseEntity.ok(counselingService.getReviewDetail(reviewId));
    }

    // 상담사 필터링
    @GetMapping("/filter")
    public ResponseEntity<List<CounselorFilterResponseDto>> filterCounselors(
            @RequestParam(required = false) String timeSlot,
            @RequestParam(required = false) String ageRange,
            @RequestParam(required = false) Boolean gender) {

        CounselorFilterRequestDto requestDto = new CounselorFilterRequestDto();
        requestDto.setTimeSlot(timeSlot);
        requestDto.setAgeRange(ageRange);
        requestDto.setGender(gender);

        return ResponseEntity.ok(counselorFilterService.filterCounselors(requestDto));
    }

    // 상담사 날짜별 상담 가능 시간 조회
    @GetMapping("/available-times/{counselorId}")
    public ResponseEntity<List<String>> getAvailableTimes(
            @PathVariable Integer counselorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(counselingService.getAvailableTimes(counselorId, date));
    }
}
