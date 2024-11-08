package com.kdt.firststep.community.service;


import com.kdt.firststep.community.dto.TipPostDTO;

public interface TipPostDetailService {
    void saveTipPost(TipPostDTO tipPostDTO);
    void updateTipPost(TipPostDTO tipPostDTO, Integer postId);
    TipPostDTO getTipPostById(Integer postId);
    void deleteTipPost(Integer postId);
}
