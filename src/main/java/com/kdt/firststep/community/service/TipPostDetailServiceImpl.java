package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.CommentDTO;
import com.kdt.firststep.community.dto.ReplyDTO;
import com.kdt.firststep.community.dto.TipPostDTO;
import com.kdt.firststep.community.repository.TipPostCommentRepository;
import com.kdt.firststep.community.repository.TipPostRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipPostDetailServiceImpl implements TipPostDetailService {
    private final TipPostRepository tipPostRepository;
    private final TipPostCommentRepository tipPostCommentRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 저장
     * @param tipPostDTO
     */
    @Override
    public void saveTipPost(TipPostDTO tipPostDTO) {
        // 추가 설정 필요할 것 같음
        // userId를 사용하여 Users 객체 조회 (임시)
        Users user = userRepository.findById(tipPostDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        tipPostRepository.save(new Posts(
                user,
                tipPostDTO.getCategory(),
                tipPostDTO.getTitle(),
                tipPostDTO.getContent()
        ));
    }

    /**
     * 게시글 수정하기
     * @param tipPostDTO
     */
    @Override
    public void updateTipPost(TipPostDTO tipPostDTO, Integer postId) {
        Posts post = tipPostRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
            post.setCategory(tipPostDTO.getCategory());
            post.setTitle(tipPostDTO.getTitle());
            post.setContent(tipPostDTO.getContent());
        tipPostRepository.save(post);
    }

    /**
     * 글 삭제
     * @param postId
     */
    @Override
    public void deleteTipPost(Integer postId){
        tipPostRepository.deleteById(postId);
    }

    /**
     *  게시글 정보 상세보기, 관련 댓글 불러오기
     * @param postId 게시글 번호
     * @return
     */
    @Override
    public TipPostDTO getTipPostById(Integer postId) {
        Posts post = tipPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));

        List<CommentDTO> commentsList = tipPostCommentRepository.findByPost_PostIdWithReplies(postId)
                .stream()
                .map(comment -> {
                    // 각 댓글의 답글 목록을 DTO로 변환
                    List<ReplyDTO> replyList = comment.getRepliesList()
                            .stream()
                            .map(reply -> new ReplyDTO(
                                    reply.getReply_Id(),
                                    reply.getUser().getUserId(),
                                    reply.getComment().getCommentId(),
                                    reply.getContent(),
                                    reply.getRegister_date(),
                                    reply.getModify_date()
                            ))
                            .collect(Collectors.toList());

                    // 댓글 정보와 함께 답글 목록도 포함하여 DTO 생성
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

        return new TipPostDTO(
                post.getPostId(),
                post.getUser().getUserId(),
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getRegisterDate(),
                post.getModifyDate(),
                post.getLikes(),
                post.getComments(),
                commentsList
        );
    }

}
