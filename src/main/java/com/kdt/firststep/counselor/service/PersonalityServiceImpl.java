package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.PersonalityAnswerOption;
import com.kdt.firststep.counselor.domain.PersonalityQuestion;
import com.kdt.firststep.counselor.dto.response.PersonalityAnswerOptionResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityQuestionResponseDto;
import com.kdt.firststep.counselor.repository.PersonalityAnswerOptionRepository;
import com.kdt.firststep.counselor.repository.PersonalityQuestionRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 조회 전용 트랜잭션으로 설정
public class PersonalityServiceImpl implements PersonalityService {

    private final UserRepository userRepository;
    private final PersonalityQuestionRepository personalityQuestionRepository;
    private final PersonalityAnswerOptionRepository personalityAnswerOptionRepository;

    /**
     * 성향 분석 여부 확인
     */
    @Override
    public PersonalityCheckResponseDto checkPersonalityStatus(Integer userId) {
        // 사용자 조회 실패시 EntityNotFoundException 발생
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // User 엔티티의 personalityCheck 값으로 DTO 생성 후 반환
        return PersonalityCheckResponseDto.from(user.getPersonalityCheck());
    }

    /**
     * 모든 활성화된 질문 조회
     */
    @Override
    public List<PersonalityQuestionResponseDto> getAllQuestions() {
        List<PersonalityQuestion> questions = personalityQuestionRepository.findAllByActiveTrue();
        return questions.stream()
                .map(PersonalityQuestionResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 질문 유형의 활성화된 질문들 조회
     */
    @Override
    public List<PersonalityQuestionResponseDto> getQuestionsByType(String questionType) {
        List<PersonalityQuestion> questions = personalityQuestionRepository
                .findAllByQuestionTypeAndActiveTrue(questionType);
        return questions.stream()
                .map(PersonalityQuestionResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 질문의 답변 옵션들 조회
     */
    @Override
    public List<PersonalityAnswerOptionResponseDto> getAnswerOptionsForQuestion(Integer questionId) {
        List<PersonalityAnswerOption> options = personalityAnswerOptionRepository
                .findAllByPersonalityQuestionQuestionId(questionId);
        return options.stream()
                .map(PersonalityAnswerOptionResponseDto::from)
                .collect(Collectors.toList());
    }

}
