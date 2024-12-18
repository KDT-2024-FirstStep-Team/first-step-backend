package com.kdt.firststep.question.service;

import com.kdt.firststep.question.domain.CoupleDailyQuestion;
import com.kdt.firststep.question.domain.QuestionAnswers;
import com.kdt.firststep.question.dto.response.QuestionWithAnswersResponseDTO;
import com.kdt.firststep.question.repository.CoupleDailyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    private final CoupleDailyQuestionRepository coupleDailyQuestionRepository;

    @Override
    public QuestionWithAnswersResponseDTO getAnswersByQuestionId(Integer questionId) {
        // 진행 중인 질문 조회 (isCompleted = false)
        CoupleDailyQuestion coupleQuestion = coupleDailyQuestionRepository
                .findByDailyQuestion_QuestionIdAndIsCompletedFalse(questionId)
                .orElseThrow(() -> new IllegalArgumentException("현재 진행 중인 질문이 없습니다."));

        // DailyQuestion에서 CoupleDailyQuestions를 조회
        List<CoupleDailyQuestion> coupleDailyQuestions =
                coupleDailyQuestionRepository.findByDailyQuestion_QuestionId(coupleQuestion.getCoupleQuestionId());

        // 답변 조회 (여기서 CoupleDailyQuestion과 연결된 답변 가져오기)
        List<QuestionAnswers> answers = coupleDailyQuestions.stream()
                .flatMap(cdq -> cdq.getQuestionAnswers().stream()) // 각 CoupleDailyQuestion에서 QuestionAnswers 가져오기
                .toList();

        // 유저와 상대방의 답변 매핑
        String userAnswer = "";
        String partnerAnswer = "";
        for (QuestionAnswers answer : answers) {
            if (answer.getUser().getUserId().equals(coupleQuestion.getMatch().getUser1().getUserId())) {
                userAnswer = answer.getAnswerComment();
            } else {
                partnerAnswer = answer.getAnswerComment();
            }
        }

        // 응답 객체 반환
        return new QuestionWithAnswersResponseDTO(
                coupleQuestion.getDailyQuestion().getQuestionId(),
                coupleQuestion.getDailyQuestion().getContent(),
                userAnswer,
                partnerAnswer,
                coupleQuestion.getIsCompleted(),
                coupleQuestion.getQuestionDate().atStartOfDay()
        );
    }
}
