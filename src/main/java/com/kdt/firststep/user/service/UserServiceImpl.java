package com.kdt.firststep.user.service;

import com.kdt.firststep.config.JwtTokenProvider;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.dto.JoinDTO;
import com.kdt.firststep.user.dto.LoginRequestDTO;
import com.kdt.firststep.user.dto.TokenDTO;
import com.kdt.firststep.user.repository.UserRepository;
import com.mchange.util.DuplicateElementException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void joinUser(JoinDTO joinDTO) {
        userRepository.findByNickname(joinDTO.getNickname()).ifPresent(users -> {
            throw new DuplicateElementException("Nickname already exists");
        });
        log.info("닉네임 중복 확인 완료", joinDTO.getNickname());
        log.info(joinDTO.getNickname());
        userRepository.save(Users.builder()
                .username(joinDTO.getUsername())
                .nickname(joinDTO.getNickname())
                .email(joinDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .birth(joinDTO.getBirth())
                .gender(joinDTO.isGender())
                .phoneNumber(joinDTO.getPhoneNumber())
                .personalityCheck(false)
                .coupleCheck(false)
                .counselorCheck(false)
                .maritalStatus(false)
                .build());

        log.info("회원가입 완료: {}", joinDTO);
    }

    @Override
    public void login(LoginRequestDTO loginRequestDTO) {
        Users user = userRepository.findByEmail(loginRequestDTO.getUserEmail())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        validatePassword(loginRequestDTO.getPassword(), user.getPassword());

        List<String> roles = getUserRoles(user);

        jwtTokenProvider.generateToken(user.getEmail(), user.getNickname(), roles);

    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!bCryptPasswordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    private List<String> getUserRoles(Users user) {
        List<String> roles = new ArrayList<>();
        if (user.getCounselorCheck()) {
            roles.add("ROLE_COUNSELOR");
        }
        if (user.getCoupleCheck()) {
            roles.add("ROLE_COUPLE");
        }
        roles.add("ROLE_USER"); // 기본 권한
        return roles;
    }
}
