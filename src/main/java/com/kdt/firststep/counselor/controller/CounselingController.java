package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.dto.request.CounselingReservationRequestDto;
import com.kdt.firststep.counselor.dto.request.CounselorDetailRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselingReservationListResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorTopResponseDto;
import com.kdt.firststep.counselor.service.CounselingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counselor")
public class CounselingController {
    private final CounselingService counselingService;

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
            @RequestParam Long userId,
            @RequestBody CounselorDetailRequestDto requestDto) {
        counselingService.createCounselorDetail(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 상담사 상세 정보 조회 API
    @GetMapping("/detail/{counselorId}")
    public ResponseEntity<CounselorDetailResponseDto> getCounselorDetail(
            @PathVariable Long counselorId) {
        return ResponseEntity.ok(counselingService.getCounselorDetail(counselorId));
    }

    // 상담사 상세 정보 수정 API
    @PutMapping("/detail/{counselorId}")
    public ResponseEntity<Void> updateCounselorDetail(
            @PathVariable Long counselorId,
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
            @RequestParam Long userId) {
        return ResponseEntity.ok(counselingService.getReservations(userId));
    }

    // 예약 승인 (상담사가 승인)
    @PutMapping("/reservation/{reservationId}/approve")
    public ResponseEntity<Void> approveReservation(@PathVariable Long reservationId) {
        counselingService.updateReservationStatus(reservationId, ReservationStatus.SCHEDULED);
        return ResponseEntity.ok().build();
    }

    // 예약 취소 (사용자가 취소)
    @PutMapping("/reservation/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        counselingService.updateReservationStatus(reservationId, ReservationStatus.CANCELLED);
        return ResponseEntity.ok().build();
    }
}
