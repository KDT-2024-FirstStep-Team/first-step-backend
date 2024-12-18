package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 조회 전용 트랜잭션으로 설정
public class PersonalityService {
    private final UserRepository userRepository;

    public PersonalityCheckResponseDto checkPersonalityStatus(Integer userId) {
        // 사용자 조회 실패시 EntityNotFoundException 발생
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // User 엔티티의 personalityCheck 값으로 DTO 생성 후 반환
        return PersonalityCheckResponseDto.from(user.getPersonalityCheck());
    }
}
