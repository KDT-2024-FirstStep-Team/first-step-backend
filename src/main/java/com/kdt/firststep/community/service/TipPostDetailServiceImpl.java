package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.CommentDTO;
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
    public void saveTipPost(TipPostDTO tipPostDTO) {
        // 추가 설정 필요할 것 같음
        // userId를 사용하여 Users 객체 조회 (임시)
        Users user = userRepository.findById(tipPostDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        tipPostRepository.save(new Posts(
                user,
                tipPostDTO.isCategory(),
                tipPostDTO.getTitle(),
                tipPostDTO.getContent()
        ));
    }

    /**
     * 게시글 수정하기
     * @param tipPostDTO
     */
    public void updateTipPost(TipPostDTO tipPostDTO, int postId) {
        Posts post = tipPostRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
            post.setCategory(tipPostDTO.isCategory());
            post.setTitle(tipPostDTO.getTitle());
            post.setContent(tipPostDTO.getContent());
        tipPostRepository.save(post);
    }


    /**
     *  게시글 정보 상세보기, 관련 댓글 불러오기
     * @param postId 게시글 번호
     * @return
     */
    public TipPostDTO getTipPostById(int postId) {
        List<CommentDTO> commentsList = tipPostCommentRepository.findByPost_PostId(postId).stream()
                .map(comment -> new CommentDTO(
                        comment.getCommentId(),
                        comment.getUser().getUserId(),
                        comment.getPost().getPostId(),
                        comment.getContent(),
                        comment.getRegisterDate(),
                        comment.getModifyDate()
                )).collect(Collectors.toList());
        Posts post = tipPostRepository.findById(postId).orElseThrow(()-> new EntityNotFoundException("게시글 못찾겠다"));
        return new TipPostDTO(
                post.getPostId(),
                post.getUser().getUserId(),
                post.isCategory(),
                post.getTitle(),
                post.getContent(),
                post.getRegisterDate(),
                post.getModifyDate(),
                post.getLikes(),
                post.getComments(),
                commentsList
                )
                ;

    }

}
