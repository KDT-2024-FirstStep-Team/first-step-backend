package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.request.PersonalityAnswerRequestDto;
import com.kdt.firststep.counselor.dto.response.PersonalityAnswerOptionResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityQuestionResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityResultResponseDto;

import java.util.List;

public interface PersonalityService {

    // 성향 분석 여부 확인
    PersonalityCheckResponseDto checkPersonalityStatus(Integer userId);

    // 모든 활성화된 질문 조회
    List<PersonalityQuestionResponseDto> getAllQuestions();

    // 특정 질문 유형의 활성화된 질문들 조회
    List<PersonalityQuestionResponseDto> getQuestionsByType(String questionType);

    // 특정 질문의 답변 옵션들 조회
    List<PersonalityAnswerOptionResponseDto> getAnswerOptionsForQuestion(Integer questionId);

    // 성향 분석 답변 제출
    void submitAnswers(PersonalityAnswerRequestDto requestDto);

    // 성향분석 결과 분석 및 저장
    void analyzeAndSaveResult(Integer userId);

    // 성향분석 결과 조회
    PersonalityResultResponseDto getPersonalityResult(Integer userId);
}
