package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.counselor.domain.SavedCounselor;
import com.kdt.firststep.counselor.repository.CounselorProfileRepository;
import com.kdt.firststep.counselor.repository.SaveCounselorRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SavedCounselorServiceImpl implements SavedCounselorService {
    private final SaveCounselorRepository savedCounselorRepository;
    private final UserRepository userRepository;
    private final CounselorProfileRepository counselorProfileRepository;

    /**
     * 상담사 찜 추가
     */
    @Override
    @Transactional
    public void saveCounselor(Integer userId, Integer counselorId) {
        // 1. 유저 존재 여부 확인
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 2. 상담사 존재 여부 및 권한 확인
        CounselorProfile counselor = counselorProfileRepository.findById(counselorId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        if (!counselor.getUser().getCounselorCheck()) {
            throw new IllegalStateException("상담사 권한이 없는 사용자입니다.");
        }

        // 3. 이미 찜한 상담사인지 확인
        if (savedCounselorRepository.existsByUserAndCounselorProfile(user, counselor)) {
            throw new IllegalStateException("이미 찜한 상담사입니다.");
        }

        // 4. 찜하기 저장
        SavedCounselor savedCounselor = SavedCounselor.builder()
                .user(user)
                .counselorProfile(counselor)
                .build();

        savedCounselorRepository.save(savedCounselor);
    }

    /**
     * 상담사 찜 삭제
     */
    @Override
    @Transactional
    public void cancelSavedCounselor(Integer userId, Integer counselorId) {
        // 1. 유저 존재 여부 확인
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 2. 상담사 존재 여부 확인
        CounselorProfile counselor = counselorProfileRepository.findById(counselorId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 상담사입니다."));

        // 3. 찜 목록에 존재하는지 확인 후 삭제
        SavedCounselor savedCounselor = savedCounselorRepository.findByUserAndCounselorProfile(user, counselor)
                .orElseThrow(() -> new EntityNotFoundException("찜 목록에 존재하지 않는 상담사입니다."));

        // 4. 찜 삭제
        savedCounselorRepository.delete(savedCounselor);
    }
}
