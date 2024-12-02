package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.LifePageResponseDTO;
import org.springframework.data.domain.Pageable;

public interface LifePostService {
    LifePageResponseDTO getLifePost(String title, Pageable pageable);
}
