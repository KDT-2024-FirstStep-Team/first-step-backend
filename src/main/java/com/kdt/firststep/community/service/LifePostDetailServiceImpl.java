package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.CommentDTO;
import com.kdt.firststep.community.dto.LifePostDTO;
import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.ReplyDTO;
import com.kdt.firststep.community.repository.LifePostCommentRepository;
import com.kdt.firststep.community.repository.LifePostRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LifePostDetailServiceImpl implements LifePostDetailService {
    private final LifePostCommentRepository lifePostCommentRepository;
    private final LifePostRepository lifePostRepository;
    private final UserRepository userRepository;

    @Override
    public void saveLifePost(LifePostDTO lifePostDTO) {
        Users user = userRepository.findById(lifePostDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        
        Posts post = Posts.builder()
                .user(user)
                .category(true)  // 쀼생 게시글은 항상 true
                .title(lifePostDTO.getTitle())
                .content(lifePostDTO.getContent())
                .build();
                
        lifePostRepository.save(post);
    }

    @Override
    public void updateLifePost(LifePostDTO lifePostDTO, Integer postId) {
        Posts post = lifePostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));
                post.setCategory(lifePostDTO.getCategory());
                post.setTitle(lifePostDTO.getTitle());
                post.setContent(lifePostDTO.getContent());
        lifePostRepository.save(post);
    }

    @Override
    public void deleteLifePost(Integer postId) {
        Posts post = lifePostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));
        lifePostRepository.delete(post);
    }

    @Override
    public LifePostDTO getLifePostById(Integer postId) {
        Posts post = lifePostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));

        // 게시글의 모든 댓글을 가져옴
        List<CommentDTO> commentsList = lifePostCommentRepository.findLifeComment(postId)
                .stream()
                .map(comment -> {
                    // 각 댓글의 답글 목록을 가져옴
                    List<ReplyDTO> replyList = comment.getRepliesList()
                            .stream()
                            .map(reply -> new ReplyDTO(
                                    reply.getReplyId(),
                                    reply.getUser().getUserId(),
                                    reply.getComment().getCommentId(),
                                    reply.getContent(),
                                    reply.getRegister_date(),
                                    reply.getModify_date()
                            ))
                            .collect(Collectors.toList());

                    // 댓글 정보와 답글 목록을 포함하여 CommentDTO 생성
                    return new CommentDTO(
                            comment.getCommentId(),
                            comment.getUser().getUserId(),
                            comment.getPost().getPostId(),
                            comment.getContent(),
                            comment.getRegisterDate(),
                            comment.getModifyDate(),
                            replyList  // 답글 목록 추가
                    );
                })
                .collect(Collectors.toList());

        return new LifePostDTO(
                post.getPostId(),
                post.getUser().getUserId(),
                false,  // 생활 게시글은 항상 false
                post.getTitle(),
                post.getContent(),
                post.getRegisterDate(),
                post.getModifyDate(),
                post.getLikes(),
                post.getComments(),
                commentsList  // 댓글 목록 추가
        );
    }
} 