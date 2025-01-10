package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.LifePageResponseDTO;
import com.kdt.firststep.community.dto.LifePostDTO;
import com.kdt.firststep.community.dto.LifePostListDTO;
import com.kdt.firststep.community.repository.LifePostRepository;
import com.kdt.firststep.counselor.repository.PersonalityStatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LifePostServiceImpl implements LifePostService {
    
    private final LifePostRepository lifePostRepository;
    private final PersonalityStatisticRepository personalityStatisticRepository;

    @Override
    public LifePageResponseDTO getLifePost(String title, Pageable pageable) {
        log.info("title: {}", title);
        Pageable effectivePageable = (pageable != null) ? pageable :PageRequest.of(0, 10, Sort.by("registerDate").descending());

        Page<Posts> posts;
        if (title == null || title.isEmpty()) {
            posts = lifePostRepository.findByCategoryTrue(effectivePageable);
            log.info("쀼생 게시판 전체 검색: {}",posts);
        } else {
            posts = lifePostRepository.findByTitleContainingAndCategoryTrue(title, effectivePageable);

            log.info("쀼생 게시글 제목 검색: {}", posts);
        }

        List<LifePostListDTO> content = posts.getContent().stream()
                .map(post -> new LifePostListDTO(
                        post.getPostId(),
                        post.getUser().getNickname(),
                        post.getTitle(),
                        post.getContent(),
                        post.getRegisterDate(),
                        post.getLikes(),
                        post.getComments()
                ))
                .collect(Collectors.toList());

        return new LifePageResponseDTO(
                content,
                posts.getNumber(),
                posts.getTotalPages(),
                posts.getTotalElements()
        );
    }
} 