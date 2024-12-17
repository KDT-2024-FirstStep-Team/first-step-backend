package com.kdt.firststep.counselor.service;

public interface SavedCounselorService {

    // 상담사 찜 추가
    void saveCounselor(Integer userId, Integer counselorId);

    // 상담사 찜 삭제
    void cancelSavedCounselor(Integer userId, Integer counselorId);

}
