package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.TipPageResponseDTO;
import org.springframework.data.domain.Pageable;


public interface TipPostService {
    TipPageResponseDTO getTipPost(String title, Pageable pageable);
}
