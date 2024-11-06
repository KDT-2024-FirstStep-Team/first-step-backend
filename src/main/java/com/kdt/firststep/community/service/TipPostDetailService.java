package com.kdt.firststep.community.service;


import com.kdt.firststep.community.dto.TipPostDTO;

public interface TipPostDetailService {
    void saveTipPost(TipPostDTO tipPostDTO);
    void updateTipPost(TipPostDTO tipPostDTO, int postId);
    TipPostDTO getTipPostById(int id);
    void deleteTipPost(int postId);
}
