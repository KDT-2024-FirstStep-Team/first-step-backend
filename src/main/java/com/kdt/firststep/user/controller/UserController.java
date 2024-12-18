package com.kdt.firststep.user.controller;

import com.kdt.firststep.user.dto.JoinDTO;
import com.kdt.firststep.user.dto.LoginRequestDTO;
import com.kdt.firststep.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * 회원가입 메소드
     * @param joinDTO
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity joinUser(@RequestBody JoinDTO joinDTO){
        log.info("유저 회원가입 : {}", joinDTO);

        userService.joinUser(joinDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO){
        userService.login(loginRequestDTO);
        return ResponseEntity.ok().build();
    }
}
