package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.dto.CommentDTO;
import com.kdt.firststep.community.service.TipPostCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/post/tip")
@RestController
@RequiredArgsConstructor
@Slf4j
public class TipPostCommentController {
    public final TipPostCommentService tipPostCommentService;

    /**
     * 댓글 달기
     * @param postId
     * @param commentDTO
     * @return
     */
    @PostMapping("/{postId}/comments")
    public ResponseEntity postComment(@PathVariable int postId,
                                      @RequestBody CommentDTO commentDTO) {
        tipPostCommentService.postComment(postId, commentDTO);
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
    public ResponseEntity updateComment(@PathVariable int postId,
                                      @PathVariable int commentId,
                                      @RequestBody CommentDTO commentDTO) {
        tipPostCommentService.updateComment(commentId, postId, commentDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 삭제
     * @param postId
     * @param commentId
     * @param commentDTO
     * @return
     */
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable int postId,
                                        @PathVariable int commentId,
                                        @RequestBody CommentDTO commentDTO) {
        tipPostCommentService.deleteComment(commentId, postId, commentDTO);
        return ResponseEntity.ok().build();
    }
}
