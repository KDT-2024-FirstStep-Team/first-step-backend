package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.TipPostDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipPostService {

    /**
     *
     * @return 모든 게시글을 불러온다.
     */
    List<TipPostDTO> getTipPost(String title, Pageable pageable);
}
