package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.ReplyDTO;
import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Replies;
import com.kdt.firststep.community.repository.LifePostCommentRepository;
import com.kdt.firststep.community.repository.LifePostReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LifePostReplyServiceImpl implements LifePostReplyService {
    
    private final LifePostCommentRepository lifePostCommentRepository;
    private final LifePostReplyRepository lifePostReplyRepository;

    @Override
    public void postReply(ReplyDTO replyDTO) {
        Comments comment = lifePostCommentRepository.findById(replyDTO.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));

        Replies reply = Replies.builder()
                .comment(comment)
                .content(replyDTO.getContent())
                .build();

        lifePostReplyRepository.save(reply);
    }
} 