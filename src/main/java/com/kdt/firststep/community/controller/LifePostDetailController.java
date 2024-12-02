package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.dto.LifePostDTO;
import com.kdt.firststep.community.service.LifePostDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/post/writing/life")
@RequiredArgsConstructor
@Slf4j
public class LifePostDetailController {
    private final LifePostDetailService lifePostDetailService;

    /**
     * 쀼생 게시글 저장
     * @param lifePostDTO
     * @return
     */
    @PostMapping
    public ResponseEntity saveLifePost(@RequestBody LifePostDTO lifePostDTO) {
        try {
            lifePostDetailService.saveLifePost(lifePostDTO);
            log.info("쀼생 게시글 저장 성공");
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("쀼생 게시글 저장 실패 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 쀼생 게시글 업데이트
     * @param lifePostDTO
     * @return
     */
    @PutMapping("/{postId}")
    public ResponseEntity updateLifePost(@RequestBody LifePostDTO lifePostDTO,
                                      @PathVariable Integer postId) {
        try {
            lifePostDetailService.updateLifePost(lifePostDTO, postId);
            log.info("쀼생 게시글 수정 성공");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("쀼생 게시글 수정 실패 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 쀼생 게시글 삭제
     * @param postId
     * @return
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity deleteLifePost(@PathVariable Integer postId) {
        try {
            lifePostDetailService.deleteLifePost(postId);
            log.info("쀼생 게시글 삭제 성공");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("쀼생 게시글 삭제 실패 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 쀼생 게시글 상세보기 + 댓글 + 답글 불러오기
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public ResponseEntity getLifePostDetail(@PathVariable Integer postId) {
        try {
            log.info("쀼생 게시글 불러오기 성공");
            return ResponseEntity.ok(lifePostDetailService.getLifePostById(postId));
        }catch (Exception e) {
            e.printStackTrace();
            log.error("쀼생 게시글을 찾지 못했습니다 : {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
} 