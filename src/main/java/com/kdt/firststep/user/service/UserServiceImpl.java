package com.kdt.firststep.user.service;

import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.dto.request.UpdateProfileRequestDTO;
import com.kdt.firststep.user.dto.response.SavedCounselorResponseDTO;
import com.kdt.firststep.user.dto.response.UserActivityDTO;
import com.kdt.firststep.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SavedCounselorResponseDTO> getSavedCounselorProfilesByUserId(Integer userId) {
        // 사용자 ID로 찜한 상담사 목록을 불러오는 로직을 구현합니다.

        return null;

    }

    @Override
    public UserActivityDTO getUserActivity(Integer userId) {

        return null;
    }

    @Override
    public UpdateProfileRequestDTO updateProfile(Integer userId, MultipartFile file, UpdateProfileRequestDTO updateProfileRequestDTO) {
        // 사용자 프로필을 업데이트하는 로직을 구현합니다.
//        Users user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // S3에 파일을 업로드하고 URL을 얻어오는 가정하에 작성합니다.
//        String profileUrl = uploadFileToS3(file); // S3 업로드 메서드를 호출하여 URL을 얻습니다.
//        user.setProfileUrl(profileUrl);
//        user.setNickname(updateProfileRequestDTO.getNickname());
//
//        userRepository.save(user);
        return updateProfileRequestDTO;
    }

    private String uploadFileToS3(MultipartFile file) {
        // 파일을 S3에 업로드하고 URL을 반환하는 로직을 작성해야 합니다.
        // 여기에 실제 S3 업로드 코드를 작성하세요.
//        "https://s3.amazonaws.com/yourbucket/" + file.getOriginalFilename();

        return null;

    }
}
