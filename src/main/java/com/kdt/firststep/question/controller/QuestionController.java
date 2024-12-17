package com.kdt.firststep.question.controller;


import com.kdt.firststep.question.dto.response.DailyQuestionResponseDTO;
import com.kdt.firststep.question.dto.response.QuestionWithAnswersResponseDTO;
import com.kdt.firststep.question.service.QuestionAnswerService;
import com.kdt.firststep.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionAnswerService questionAnswerService;

    @GetMapping
    public ResponseEntity<List<DailyQuestionResponseDTO>> getAllQuestions() {
        List<DailyQuestionResponseDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionWithAnswersResponseDTO> getQuestionById(
            @PathVariable("questionId") Integer questionId) {
        QuestionWithAnswersResponseDTO question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    /*
        요청(Request Body)

        userId: 답변을 작성하는 유저 ID
        answerComment: 답변 내용
        구현 과정:

        PathVariable로 questionId를 받고 RequestBody로 답변 내용을 받습니다.
        questionId에 해당하는 CoupleDailyQuestion를 조회합니다.
        QuestionAnswers 엔티티를 생성하고 저장합니다.
    */
//    @PostMapping("/daily/{questionId}/answers")
//    public ResponseEntity<ApiResponseDTO> saveAnswer(
//            @PathVariable("questionId") Integer questionId,
//            @RequestBody AnswerRequestDTO request) {
//        questionAnswerService.saveAnswer(questionId, request);
//        return ResponseEntity.ok(new ApiResponseDTO("답변 저장 성공"));
//    }

    @GetMapping("/daily/{questionId}/answers")
    public ResponseEntity<QuestionWithAnswersResponseDTO> getQuestionWithAnswers(
            @PathVariable("questionId") Integer questionId) {
        QuestionWithAnswersResponseDTO response = questionAnswerService.getAnswersByQuestionId(questionId);
        return ResponseEntity.ok(response);
    }

}
