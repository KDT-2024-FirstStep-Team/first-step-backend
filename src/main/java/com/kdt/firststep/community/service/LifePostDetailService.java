package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.LifePostDTO;
import org.springframework.transaction.annotation.Transactional;

public interface LifePostDetailService {
    void saveLifePost(LifePostDTO lifePostDTO);

    void updateLifePost(LifePostDTO lifePostDTO, Integer postId);

    void deleteLifePost(Integer postId);

    LifePostDTO getLifePostById(Integer postId);
}
