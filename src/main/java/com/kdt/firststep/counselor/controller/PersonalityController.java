package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.dto.request.PersonalityAnswerRequestDto;
import com.kdt.firststep.counselor.dto.response.PersonalityAnswerOptionResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityQuestionResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityResultResponseDto;
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
     * 성향 분석 여부 확인 API
     * @param userId
     */
    @GetMapping("/check")
    public ResponseEntity<PersonalityCheckResponseDto> checkPersonality(@RequestParam Integer userId) {
        return ResponseEntity.ok(personalityService.checkPersonalityStatus(userId));
    }

    /**
     * 성향분석 모든 질문 조회 API
     */
    @GetMapping("/questions")
    public ResponseEntity<List<PersonalityQuestionResponseDto>> getAllQuestions() {
        return ResponseEntity.ok(personalityService.getAllQuestions());
    }

    /**
     * 성향분석 답변 제출 API
     * @param requestDto 사용자의 답변 정보 (userId와 질문별 선택한 옵션 목록)
     */
    @PostMapping("/answers")
    public ResponseEntity<String> submitAnswers(@RequestBody PersonalityAnswerRequestDto requestDto) {
        personalityService.submitAnswers(requestDto);
        return ResponseEntity.ok("성향분석이 완료되었습니다.");
    }

    /**
     * 성향분석 결과 조회 API
     * @param userId
     */
    @GetMapping("/result")
    public ResponseEntity<PersonalityResultResponseDto> getPersonalityResult(@RequestParam Integer userId) {
        return ResponseEntity.ok(personalityService.getPersonalityResult(userId));
    }

    /**
     * 성향분석 특정유형 질문 조회 API
     * @param questionType
     */
    @GetMapping("/questions/type")
    public ResponseEntity<List<PersonalityQuestionResponseDto>> getQuestionsByType(
            @RequestParam String questionType) {
        return ResponseEntity.ok(personalityService.getQuestionsByType(questionType));
    }

    /**
     * 성향분석 질문별 답변 옵션 조회 API
     * @param questionId
     */
    @GetMapping("/questions/{questionId}/options")
    public ResponseEntity<List<PersonalityAnswerOptionResponseDto>> getAnswerOptionsForQuestion(
            @PathVariable Integer questionId) {
        return ResponseEntity.ok(personalityService.getAnswerOptionsForQuestion(questionId));
    }
}
