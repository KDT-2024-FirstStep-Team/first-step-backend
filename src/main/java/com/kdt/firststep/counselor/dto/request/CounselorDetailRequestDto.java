package com.kdt.firststep.counselor.dto.request;

import lombok.Getter;

@Getter
public class CounselorDetailRequestDto {
    private String introduction;        // 소개글
    private String specialties;         // 전문 분야
    private Integer consultationFee;    // 상담 비용
    private String availableDays;       // 상담 가능 요일("평일", "주말", "모든요일")
    private String startTime;           // 상담 시작 시간
    private String endTime;             // 상담 종료 시간
}
