package com.kdt.firststep.user.joinController;

import com.kdt.firststep.user.dto.JoinDTO;
import com.kdt.firststep.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
public class JoinController {
    private final UserRepository userRepository;
    @PostMapping
    public ResponseEntity joinUser(JoinDTO joinDTO){
        return null;
    }
}
