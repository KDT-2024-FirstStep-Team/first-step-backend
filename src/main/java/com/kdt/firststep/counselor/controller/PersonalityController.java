package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.dto.response.PersonalityAnswerOptionResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityQuestionResponseDto;
import com.kdt.firststep.counselor.service.PersonalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personality")
public class PersonalityController {
    private final PersonalityService personalityService;

    /**
     * 성향 분석 여부 확인
     * @param userId
     */
    @GetMapping("/check")
    public ResponseEntity<PersonalityCheckResponseDto> checkPersonality(@RequestParam Integer userId) {
        return ResponseEntity.ok(personalityService.checkPersonalityStatus(userId));
    }

    /**
     * 성향분석 모든 질문 조회
     */
    @GetMapping("/questions")
    public ResponseEntity<List<PersonalityQuestionResponseDto>> getAllQuestions() {
        return ResponseEntity.ok(personalityService.getAllQuestions());
    }

    /**
     * 성향분석 특정유형 질문 조회
     * @param questionType
     */
    @GetMapping("/questions/type")
    public ResponseEntity<List<PersonalityQuestionResponseDto>> getQuestionsByType(
            @RequestParam String questionType) {
        return ResponseEntity.ok(personalityService.getQuestionsByType(questionType));
    }

    /**
     * 성향분석 질문별 답변 옵션 조회
     * @param questionId
     */
    @GetMapping("/questions/{questionId}/options")
    public ResponseEntity<List<PersonalityAnswerOptionResponseDto>> getAnswerOptionsForQuestion(
            @PathVariable Integer questionId) {
        return ResponseEntity.ok(personalityService.getAnswerOptionsForQuestion(questionId));
    }
}
