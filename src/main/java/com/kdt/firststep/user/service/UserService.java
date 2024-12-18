package com.kdt.firststep.user.service;


public interface UserService {
    void joinUser(JoinDTO joinDTO);

    void login(LoginRequestDTO loginRequestDTO);
    // 찜한 상담사 불러오기
    List<SavedCounselorResponseDTO> getSavedCounselorProfilesByUserId(Integer userId);

    // 내 상담사 불러오기(상담관리)
//    List<CounselorProfile> getCounselorProfilesByUserId(Integer userId);

    // 내가 쓴 글, 나의 댓글, 공감한 글, 저장한 글
    UserActivityDTO getUserActivity(Integer userId);

    // 프로필 정보 업데이트
    UpdateProfileResponseDTO updateProfileWithUrl(Integer userId, String file, UpdateProfileRequestDTO updateProfileRequestDTO);

    List<CounselorReservationDTO> getCounselorReservations(Integer userId);
}
