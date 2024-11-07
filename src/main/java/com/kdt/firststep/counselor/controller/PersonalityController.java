package com.kdt.firststep.counselor.controller;

import com.kdt.firststep.counselor.dto.response.PersonalityCheckResponseDto;
import com.kdt.firststep.counselor.service.PersonalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personality")
public class PersonalityController {
    private final PersonalityService personalityService;

    @GetMapping("/check")
    public ResponseEntity<PersonalityCheckResponseDto> checkPersonality(@RequestParam Long userId) {
        return ResponseEntity.ok(personalityService.checkPersonalityStatus(userId));
    }
}
