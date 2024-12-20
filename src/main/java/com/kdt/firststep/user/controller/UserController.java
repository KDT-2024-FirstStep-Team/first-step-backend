package com.kdt.firststep.user.controller;

import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.dto.JoinDTO;
import com.kdt.firststep.user.dto.LoginRequestDTO;
import com.kdt.firststep.user.dto.request.UpdateProfileRequestDTO;
import com.kdt.firststep.user.dto.response.CounselorReservationDTO;
import com.kdt.firststep.user.dto.response.SavedCounselorResponseDTO;
import com.kdt.firststep.user.dto.response.UpdateProfileResponseDTO;
import com.kdt.firststep.user.repository.UserRepository;
import com.kdt.firststep.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * 회원가입 메소드
     * @param joinDTO
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity joinUser(@RequestBody JoinDTO joinDTO) {
        log.info("유저 회원가입 : {}", joinDTO);

        userService.joinUser(joinDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/users/{userId}/counselors")
    public ResponseEntity<List<CounselorReservationDTO>> getCounselorReservations(
                @PathVariable("userId") Integer userId){
            List<CounselorReservationDTO> reservations = userService.getCounselorReservations(userId);
            return ResponseEntity.ok(reservations);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO) {
        userService.login(loginRequestDTO);
        return ResponseEntity.ok().build();
    }
    // 찜한 상담사 불러오기 (일단 완료)
    @GetMapping("/users/{userId}/saved-counselor")
    public ResponseEntity<List<SavedCounselorResponseDTO>> getUserSavedCounselor(@PathVariable("userId") Integer userId){
        List<SavedCounselorResponseDTO> savedCounselors = userService.getSavedCounselorProfilesByUserId(userId);
        return ResponseEntity.ok(savedCounselors);
    }

    // 프로필 정보 업데이트 | 이미지(profile_url), 닉네임(nickname) -> S3와 연결
    @PutMapping("/users/{userId}/profile")
    public  ResponseEntity<UpdateProfileResponseDTO> updateUserProfile(
        @PathVariable("userId") Integer userId,
        @Valid @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO) {

        UpdateProfileResponseDTO responseDTO  = userService.updateProfileWithUrl(userId, updateProfileRequestDTO.getFile(),updateProfileRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 보유하트 불러오기  (완료)
    @GetMapping("/users/{userId}/coin")
    public ResponseEntity<Integer> getUserCoin(@PathVariable("userId") Integer userId){

        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("UserController getUserCoin method error" ));

        return ResponseEntity.ok(user.getCoin());
    }

}
