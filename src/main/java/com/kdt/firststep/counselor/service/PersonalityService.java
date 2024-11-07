package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.counselor.repository.UserRepository;
import com.kdt.firststep.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 조회 전용 트랜잭션으로 설정
public class PersonalityService {
    private final UserRepository userRepository;

    public PersonalityCheckResponseDto checkPersonalityStatus(Long userId) {
        // 사용자 조회 실패시 EntityNotFoundException 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // User 엔티티의 personalityCheck 값으로 DTO 생성 후 반환
        return PersonalityCheckResponseDto.from(user.isPersonalityCheck());
    }
}
