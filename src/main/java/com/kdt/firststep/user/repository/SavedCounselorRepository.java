package com.kdt.firststep.user.repository;

import com.kdt.firststep.counselor.domain.SavedCounselor;
import com.kdt.firststep.user.dto.response.SavedCounselorResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedCounselorRepository extends JpaRepository<SavedCounselor, Long> {




//    List<SavedCounselorResponseDTO> findSavedCounselorByUserId(Long userId);
}
