package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.service.SavedCounselorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counselor")
public class SavedCounselorController {

    private final SavedCounselorService savedCounselorService;

    /**
     * 상담사 찜 추가
     * @param counselorId
     * @param userId
     */
    @PostMapping("/{counselorId}/save")
    public ResponseEntity<String> saveCounselor(
            @PathVariable Integer counselorId,
            @RequestParam Integer userId) {
        savedCounselorService.saveCounselor(userId, counselorId);
        return ResponseEntity.ok("상담사 찜 추가 완료");
    }

    /**
     * 상담사 찜 삭제
     * @param counselorId
     * @param userId
     */
    @DeleteMapping("/{counselorId}/save")
    public ResponseEntity<String> cancelSavedCounselor(
            @PathVariable Integer counselorId,
            @RequestParam Integer userId) {
        savedCounselorService.cancelSavedCounselor(userId, counselorId);
        return ResponseEntity.ok("상담사 찜 삭제 완료");
    }
}
