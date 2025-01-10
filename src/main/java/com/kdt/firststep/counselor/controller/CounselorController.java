package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.dto.request.CounselorDetailRequestDto;
import com.kdt.firststep.counselor.dto.request.CounselorFilterRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselorDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorFilterResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorTopResponseDto;
import com.kdt.firststep.counselor.service.CounselorFilterService;
import com.kdt.firststep.counselor.service.CounselorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/counselor")
public class CounselorController {
    private final CounselorService counselorService;
    private final CounselorFilterService counselorFilterService;

    /**
     * 만족도 순 TOP5 상담사 목록 조회 (평점 순) API
     */
    @GetMapping("/top/satisfaction")
    public ResponseEntity<List<CounselorTopResponseDto>> getTopCounselors() {
        return ResponseEntity.ok(counselorService.getTopCounselorsByRating());
    }

    /**
     * 인기 순 TOP5 상담사 목록 조회 (완료된 상담이 많은 순) API
     */
    @GetMapping("/top/popularity")
    public ResponseEntity<List<CounselorTopResponseDto>> getTopCounselorsByPopularity() {
        return ResponseEntity.ok(counselorService.getTopCounselorsByPopularity());
    }

    /**
     * 검색 - 닉네임과 일치하는 상담사 조회 API
     * @param keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<CounselorTopResponseDto>> searchCounselors(
            @RequestParam String keyword) {
        return ResponseEntity.ok(counselorService.searchCounselorsByNickname(keyword));
    }

    /**
     * 상담사 상세 프로필 생성 API
     * @param userId
     * @param requestDto
     */
    @PostMapping("/detail")
    public ResponseEntity<Void> createCounselorDetail(
            @RequestParam Integer userId,
            @RequestBody CounselorDetailRequestDto requestDto) {
        counselorService.createCounselorDetail(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 상담사 상세 프로필 조회 API
     * @param counselorId
     */
    @GetMapping("/detail/{counselorId}")
    public ResponseEntity<CounselorDetailResponseDto> getCounselorDetail(
            @PathVariable Integer counselorId) {
        return ResponseEntity.ok(counselorService.getCounselorDetail(counselorId));
    }

    /**
     * 상담사 상세 프로필 수정 API
     * @param counselorId
     * @param requestDto
     */
    @PutMapping("/detail/{counselorId}")
    public ResponseEntity<Void> updateCounselorDetail(
            @PathVariable Integer counselorId,
            @RequestBody CounselorDetailRequestDto requestDto) {
        counselorService.updateCounselorDetail(counselorId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 필터별 상담사 조회(상담 가능 시간대, 연령대, 성별) API
     * @param timeSlot
     * @param ageRange
     * @param gender
     */
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
}
