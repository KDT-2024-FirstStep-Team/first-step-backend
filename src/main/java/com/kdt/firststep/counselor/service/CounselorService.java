package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.dto.request.CounselorDetailRequestDto;
import com.kdt.firststep.counselor.dto.response.CounselorDetailResponseDto;
import com.kdt.firststep.counselor.dto.response.CounselorTopResponseDto;

import java.util.List;

public interface CounselorService {

    // 만족도 순 TOP5 상담사 목록 조회 (평점 순)
    List<CounselorTopResponseDto> getTopCounselorsByRating();

    // 인기 순 TOP5 상담사 목록 조회 (완료된 상담이 많은 순)
    List<CounselorTopResponseDto> getTopCounselorsByPopularity();

    // 검색 - 닉네임과 일치하는 상담사 조회
    List<CounselorTopResponseDto> searchCounselorsByNickname(String keyword);

    // 상담사 상세 프로필 생성
    void createCounselorDetail(Integer userId, CounselorDetailRequestDto requestDto);

    // 상담사 상세 프로필 조회
    CounselorDetailResponseDto getCounselorDetail(Integer counselorId);

    // 상담사 상세 프로필 수정
    void updateCounselorDetail(Integer counselorId, CounselorDetailRequestDto requestDto);

}
