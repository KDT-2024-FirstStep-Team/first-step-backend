package com.kdt.firststep.user.controller;


import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.dto.request.UpdateProfileRequestDTO;
import com.kdt.firststep.user.dto.response.SavedCounselorResponseDTO;
import com.kdt.firststep.user.dto.response.UpdateProfileResponseDTO;
import com.kdt.firststep.user.dto.response.UserActivityDTO;
import com.kdt.firststep.user.repository.UserRepository;
import com.kdt.firststep.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // 내가 쓴 글, 나의 댓글, 공감한 글, 저장한 글(게시글) (수정 필요 -> 내가 쓴 글, 나의 댓글, 저장한 글)
    @GetMapping("/{userId}/activity")
    public ResponseEntity<UserActivityDTO> getUserActivity(@PathVariable("userId") Integer userId) {

        UserActivityDTO activity = userService.getUserActivity(userId);
        return ResponseEntity.ok(activity);
    }

    // 찜한 상담사 불러오기 (일단 완료)
    @GetMapping("/{userId}/savedCounselor")
    public ResponseEntity<List<SavedCounselorResponseDTO>> getUserSavedCounselor(@PathVariable("userId") Integer userId){

        List<SavedCounselorResponseDTO> savedCounselors = userService.getSavedCounselorProfilesByUserId(userId);
        return ResponseEntity.ok(savedCounselors);
    }

    // 프로필 정보 업데이트 | 이미지(profile_url), 닉네임(nickname) -> S3와 연결
    @PutMapping("/{userId}/profile")
    public  ResponseEntity<UpdateProfileResponseDTO> updateUserProfile(
        @PathVariable("userId") Integer userId,
        @RequestParam("file") MultipartFile file,
        @Valid @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO){

        UpdateProfileResponseDTO updatedProfile = userService.updateProfile(userId, file,  updateProfileRequestDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    // 보유하트 불러오기  (일단 완료)
    @GetMapping("/{userId}/coin")
    public ResponseEntity<Integer> getUserCoin(@PathVariable("userId") Integer userId){

        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("UserController getUserCoin method error" ));

        return ResponseEntity.ok(user.getCoin());
    }

}
