package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.dto.CommentDTO;
import com.kdt.firststep.community.service.LifePostCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/post/life")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LifePostCommentController {
    private final LifePostCommentService lifePostCommentService;

    /**
     * 댓글 달기
     * @param postId
     * @param commentDTO
     * @return
     */
    @PostMapping("/{postId}/comments")
    public ResponseEntity postComment(@PathVariable int postId,
                                      @RequestBody CommentDTO commentDTO) {
        log.info("postComment CommentDTO : {}", commentDTO);

        lifePostCommentService.postComment(postId, commentDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 수정하기
     * @param postId
     * @param commentId
     * @param commentDTO
     * @return
     */
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Integer postId,
                                      @PathVariable Integer commentId,
                                      @RequestBody CommentDTO commentDTO) {
        lifePostCommentService.updateComment(commentId, postId, commentDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 삭제
     * @param postId
     * @param commentId
     * @return
     */
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer postId,
                                      @PathVariable Integer commentId) {
        lifePostCommentService.deleteComment(commentId, postId);
        return ResponseEntity.ok().build();
    }
} 