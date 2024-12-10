package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.ReservationStatus;
import com.kdt.firststep.counselor.dto.request.CounselingReservationRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselingReservationListResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface CounselingService {

    // 상담 예약 생성
    void createReservation(CounselingReservationRequestDto requestDto);

    // 사용자 상담 예약 정보 조회
    List<CounselingReservationListResponseDto> getReservations(Integer userId);

    // 상담 예약 상태 변경 (상담사 예약 승인,사용자 예약 취소)
    void updateReservationStatus(Integer reservationId, ReservationStatus newStatus);

    // 만료된 상담 예약 자동 완료 처리
    void completeExpiredReservations();

    // 상담사 날짜별 상담 예약 가능 시간 조회
    List<String> getAvailableTimes(Integer counselorId, LocalDate date);

}
