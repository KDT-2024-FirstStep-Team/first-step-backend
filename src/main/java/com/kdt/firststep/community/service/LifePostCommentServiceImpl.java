package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.CommentDTO;
import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.repository.LifePostCommentRepository;
import com.kdt.firststep.community.repository.LifePostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LifePostCommentServiceImpl implements LifePostCommentService {

    private final LifePostRepository lifePostRepository;
    private final LifePostCommentRepository lifePostCommentRepository;

    @Override
    public void postComment(Integer postId, CommentDTO commentDTO) {
        Posts post = lifePostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));

        Comments comment = Comments.builder()
                .post(post)
                .content(commentDTO.getContent())
                .build();

        lifePostCommentRepository.save(comment);
    }

    @Override
    public void updateComment(Integer commentId, Integer postId, CommentDTO commentDTO) {
        Comments comment = lifePostCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));
        comment.setContent(commentDTO.getContent());
        lifePostCommentRepository.save(comment);
    }

    @Override
    public void deleteComment(Integer commentId, Integer postId) {
        lifePostCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));

        log.info("쀼생 댓글 삭제 완료");
        lifePostCommentRepository.deleteById(commentId);
    }
}