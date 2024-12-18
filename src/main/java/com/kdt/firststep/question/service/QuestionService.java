package com.kdt.firststep.question.service;

import com.kdt.firststep.question.dto.response.DailyQuestionResponseDTO;
import com.kdt.firststep.question.dto.response.QuestionWithAnswersResponseDTO;

import java.util.List;

public interface QuestionService {

    List<DailyQuestionResponseDTO> getAllQuestions();

    QuestionWithAnswersResponseDTO getQuestionById(Integer questionId);
}
