package com.kdt.firststep.community.service;


import com.kdt.firststep.community.dto.TipPostDTO;

public interface TipPostDetailService {
    void saveTipPost(TipPostDTO tipPostDTO);
    TipPostDTO getTipPostById(int id);
}
