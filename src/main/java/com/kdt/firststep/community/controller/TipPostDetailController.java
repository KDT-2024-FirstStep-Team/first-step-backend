package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.dto.TipPostDTO;
import com.kdt.firststep.community.service.TipPostDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/post/writing")
@RequiredArgsConstructor
@Slf4j
public class TipPostDetailController {
    private final TipPostDetailService tipPostDetailService;

    /**
     *
     * @param tipPostDTO
     * @return 쀼팁 게시글 저장
     */
    @PostMapping("/tip")
    public ResponseEntity saveTipPost(@RequestBody TipPostDTO tipPostDTO) {
        try {
            tipPostDetailService.saveTipPost(tipPostDTO);
            log.error("쀼팁 게시글 저장 성공");
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("쀼팁 게시글 저장 실패 : {}", e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity getTipPostDetail(@PathVariable int postId) {
        return ResponseEntity.ok(tipPostDetailService.getTipPostById(postId));
    }

}
