package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.dto.response.RecommendedCounselorResponseDto;
import com.kdt.firststep.counselor.service.CounselorMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counselor/matching")
public class CounselorMatchingController {
    private final CounselorMatchingService counselorMatchingService;

    /**
     * 맞춤 상담사 TOP 5 추천
     * @param userId 사용자  Id
     */
    @GetMapping("/recommended")
    public ResponseEntity<List<RecommendedCounselorResponseDto>> getRecommendedCounselors(
            @RequestParam Integer userId) {
        return ResponseEntity.ok(counselorMatchingService.getRecommendedCounselors(userId));
    }
}