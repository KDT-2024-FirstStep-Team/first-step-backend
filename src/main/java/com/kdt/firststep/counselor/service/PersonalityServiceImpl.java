package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.PersonalityAnswerOption;
import com.kdt.firststep.counselor.domain.PersonalityQuestion;
import com.kdt.firststep.counselor.domain.UserPersonalityAnswer;
import com.kdt.firststep.counselor.dto.request.PersonalityAnswerRequestDto;
import com.kdt.firststep.counselor.dto.response.PersonalityAnswerOptionResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.counselor.dto.response.PersonalityQuestionResponseDto;
import com.kdt.firststep.counselor.repository.PersonalityAnswerOptionRepository;
import com.kdt.firststep.counselor.repository.PersonalityQuestionRepository;
import com.kdt.firststep.counselor.repository.UserPersonalityAnswerRepository;
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
    private final UserPersonalityAnswerRepository userPersonalityAnswerRepository;

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

    /**
     * 성향분석 답변 제출
     * @param requestDto 사용자의 답변 정보 (userId와 질문별 선택한 옵션 목록)
     */
    @Override
    @Transactional
    public void submitAnswers(PersonalityAnswerRequestDto requestDto) {
        // 답변을 제출한 사용자가 존재하는지 확인
        Users user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 이미 성향분석을 완료한 사용자라면 기존 답변 삭제
        if (user.getPersonalityCheck()) {
            userPersonalityAnswerRepository.deleteAllByUser(user);
            userPersonalityAnswerRepository.flush(); // 즉시 반영
        }

        // 새로운 답변들을 생성
        List<UserPersonalityAnswer> newAnswers = requestDto.getAnswers().stream()
                .map(answerDetail -> {
                    // 답변한 질문이 실제 존재하는지 확인
                    PersonalityQuestion question = personalityQuestionRepository
                            .findById(answerDetail.getQuestionId())
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 질문입니다."));

                    // 선택한 답변 옵션이 실제 존재하는지 확인
                    PersonalityAnswerOption selectedOption = personalityAnswerOptionRepository
                            .findById(answerDetail.getOptionId())
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 답변 옵션입니다."));

                    // 사용자의 답변 엔티티 생성
                    // plusScore 는 선택한 옵션의 점수(1,2,3)
                    return UserPersonalityAnswer.builder()
                            .user(user)
                            .personalityQuestion(question)
                            .selectedOption(selectedOption)
                            .plusScore(selectedOption.getScore())
                            .build();
                })
                .collect(Collectors.toList());

        // 모든 답변을 데이터베이스에 저장
        userPersonalityAnswerRepository.saveAll(newAnswers);

        // 사용자의 성향분석 완료 상태를 true 로 업데이트
        user.setPersonalityCheck(true);
        userRepository.save(user);
    }

}
