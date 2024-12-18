package com.kdt.firststep.question.service;


import com.kdt.firststep.question.domain.DailyQuestion;
import com.kdt.firststep.question.dto.response.DailyQuestionResponseDTO;
import com.kdt.firststep.question.dto.response.QuestionWithAnswersResponseDTO;
import com.kdt.firststep.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {


    private final QuestionRepository questionRepository;

    public List<DailyQuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(dq -> new DailyQuestionResponseDTO(
                        dq.getQuestionId(),
                        dq.getContent(),
                        dq.getCreatedAt(),
                        dq.getActive()
                )).collect(Collectors.toList());
    }

    public QuestionWithAnswersResponseDTO getQuestionById(Integer questionId) {
        DailyQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 질문이 존재하지 않습니다."));

        // 답변 데이터 가져오기 : 예시
        String userAnswer = "사용자 답변은 ~~ 입니다.";
        String partnerAnswer = "상대방 답변은 ~~ 입니다";
        Boolean isCompleted = true;
        LocalDateTime answeredAt = LocalDateTime.now();

        return new QuestionWithAnswersResponseDTO(
                question.getQuestionId(),
                question.getContent(),
                userAnswer,
                partnerAnswer,
                isCompleted,
                answeredAt
        );
    }
}
