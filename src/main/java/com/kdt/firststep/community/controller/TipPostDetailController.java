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
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 게시글 업데이트
     * @param tipPostDTO
     * @return
     */
    @PutMapping("/tip/{postId}")
    public ResponseEntity updateTipPost(@RequestBody TipPostDTO tipPostDTO,
                                        @PathVariable int postId) {
        try {
            tipPostDetailService.updateTipPost(tipPostDTO, postId); // 수정용 서비스 메서드 사용
            log.info("게시글 수정 성공");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("게시글 수정 실패 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 게시글 삭제
     * @param postId
     * @return
     */
    @DeleteMapping("/tip/{postId}")
    public ResponseEntity deleteTipPost(@PathVariable int postId) {
        try {
            tipPostDetailService.deleteTipPost(postId); // 수정용 서비스 메서드 사용
            log.info("게시글 삭제 성공");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("게시글 삭제 실패 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 게시글 상세보기 + 댓글 불러오기
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public ResponseEntity getTipPostDetail(@PathVariable int postId) {
        try {
            ResponseEntity.ok(tipPostDetailService.getTipPostById(postId));
            log.info("게시글 불러오기 성공");
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            e.printStackTrace();
            log.error("게시글을 찾지 못했습니다 : {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

}
