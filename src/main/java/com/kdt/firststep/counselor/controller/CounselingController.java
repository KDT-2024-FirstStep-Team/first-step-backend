package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.dto.request.*;
import com.kdt.firststep.counselor.dto.response.*;
import com.kdt.firststep.counselor.service.CounselingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counseling")
public class CounselingController {

    private final CounselingService counselingService;

    /**
     * 상담 예약 생성 API
     * @param requestDto
     */
    @PostMapping("/reservation")
    public ResponseEntity<Void> createReservation(@RequestBody CounselingReservationRequestDto requestDto) {
        counselingService.createReservation(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 사용자 상담 예약 정보 조회 API
     * @param userId
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<CounselingReservationListResponseDto>> getReservations(
            @RequestParam Integer userId) {
        return ResponseEntity.ok(counselingService.getReservations(userId));
    }

    /**
     * 상담 예약 상태 변경 (상담사 예약 승인) API
     * @param reservationId
     */
    @PutMapping("/reservation/{reservationId}/approve")
    public ResponseEntity<Void> approveReservation(@PathVariable Integer reservationId) {
        counselingService.updateReservationStatus(reservationId, ReservationStatus.SCHEDULED);
        return ResponseEntity.ok().build();
    }

    /**
     * 상담 예약 상태 변경 (사용자 예약 취소) API
     * @param reservationId
     */
    @PutMapping("/reservation/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Integer reservationId) {
        counselingService.updateReservationStatus(reservationId, ReservationStatus.CANCELLED);
        return ResponseEntity.ok().build();
    }

    /**
     * 상담사 날짜별 상담 예약 가능 시간 조회 API
     * @param counselorId
     * @param date
     */
    @GetMapping("/available-times/{counselorId}")
    public ResponseEntity<List<String>> getAvailableTimes(
            @PathVariable Integer counselorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(counselingService.getAvailableTimes(counselorId, date));
    }

}
