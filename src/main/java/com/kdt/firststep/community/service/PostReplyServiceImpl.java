package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Replies;
import com.kdt.firststep.community.dto.ReplyDTO;
import com.kdt.firststep.community.repository.PostReplyRepository;
import com.kdt.firststep.community.repository.TipPostCommentRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReplyServiceImpl implements PostReplyService {
    private final PostReplyRepository postReplyRepository;
    private final UserRepository userRepository;
    private final TipPostCommentRepository tipPostCommentRepository;

    @Override
    public void postReply(ReplyDTO replyDTO) {
        Users user = userRepository.findById(replyDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        Comments comments = tipPostCommentRepository.findById(replyDTO.getCommentId()).orElseThrow(EntityNotFoundException::new);

        postReplyRepository.save(new Replies(
                user,
                comments,
                replyDTO.getContent()
        ));
    }
}
