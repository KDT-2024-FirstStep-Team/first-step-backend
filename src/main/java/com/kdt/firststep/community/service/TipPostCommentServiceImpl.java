package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.CommentDTO;
import com.kdt.firststep.community.repository.TipPostCommentRepository;
import com.kdt.firststep.community.repository.TipPostRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipPostCommentServiceImpl implements TipPostCommentService {
    private final TipPostCommentRepository tipPostCommentRepository;
    private final TipPostRepository tipPostRepository;
    private final UserRepository userRepository;

    @Override
    public void postComment(int postId, CommentDTO commentDTO) {
        Users user = userRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        Posts post = tipPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        tipPostCommentRepository.save(new Comments(
                user,
                post,
                commentDTO.getContent()
        ));
    }

    /**
     * 댓글 수정하기
     * @param commentId
     * @param postId
     * @param commentDTO
     */
    @Override
    public void updateComment(int commentId, int postId, CommentDTO commentDTO) {
        Comments comments = tipPostCommentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        comments.setContent(commentDTO.getContent());
        tipPostCommentRepository.save(comments);
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @param postId
     * @param commentDTO
     */
    @Override
    public void deleteComment(int commentId, int postId, CommentDTO commentDTO) {
        tipPostCommentRepository.deleteById(commentId);
    }
}
