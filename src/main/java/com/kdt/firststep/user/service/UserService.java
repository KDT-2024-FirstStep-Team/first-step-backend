package com.kdt.firststep.user.service;

import com.kdt.firststep.user.dto.request.UpdateProfileRequestDTO;
import com.kdt.firststep.user.dto.response.SavedCounselorResponseDTO;
import com.kdt.firststep.user.dto.response.UpdateProfileResponseDTO;
import com.kdt.firststep.user.dto.response.UserActivityDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    // 찜한 상담사 불러오기
    List<SavedCounselorResponseDTO> getSavedCounselorProfilesByUserId(Integer userId);

    // 내 상담사 불러오기(상담관리)
//    List<CounselorProfile> getCounselorProfilesByUserId(Integer userId);

    // 내가 쓴 글, 나의 댓글, 공감한 글, 저장한 글
    UserActivityDTO getUserActivity(Integer userId);

    // 프로필 정보 업데이트
    UpdateProfileResponseDTO updateProfile(Integer userId, MultipartFile file, UpdateProfileRequestDTO updateProfileRequestDTO);

}
