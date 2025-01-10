package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.TipPageResponseDTO;
import com.kdt.firststep.community.dto.TipPostDTO;
import com.kdt.firststep.community.dto.TipPostListDTO;
import com.kdt.firststep.community.repository.TipPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipPostServiceImpl implements TipPostService {
    private final TipPostRepository tipPostRepository;

    @Override
    public TipPageResponseDTO getTipPost(String title, Pageable pageable) {

        Pageable effectivePageable = (pageable != null) ? pageable : PageRequest.of(0, 10, Sort.by("registerDate").descending());

        Page<Posts> posts;
        if (title == null || title.isEmpty()) {
            posts = tipPostRepository.findByCategoryFalse(effectivePageable);
            log.info("꿀팁 게시판 전체 검색: {}",posts);
        } else {
            posts = tipPostRepository.findByTitleContainingAndCategoryFalse(title, effectivePageable);

            log.info("꿀팁 게시글 제목 검색: {}", posts);
        }

        List<TipPostListDTO> content = posts.getContent().stream()
                .map(post -> new TipPostListDTO(
                        post.getPostId(),
                        post.getUser().getNickname(),
                        post.getTitle(),
                        post.getContent(),
                        post.getRegisterDate(),
                        post.getLikes(),
                        post.getComments(),
                        post.getPostImageList().isEmpty() ? null : post.getPostImageList().get(0).getImageUrl()
                        ))
                .collect(Collectors.toList());

        return new TipPageResponseDTO(
                content,
                posts.getNumber(),
                posts.getTotalPages(),
                posts.getTotalElements()
        );
    }
}
