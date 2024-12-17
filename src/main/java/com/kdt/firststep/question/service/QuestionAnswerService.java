package com.kdt.firststep.question.service;

import com.kdt.firststep.question.dto.response.QuestionWithAnswersResponseDTO;

public interface QuestionAnswerService {
    QuestionWithAnswersResponseDTO getAnswersByQuestionId(Integer questionId);
}
