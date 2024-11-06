package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.TipPostDTO;
import com.kdt.firststep.community.repository.TipPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipPostServiceImpl implements TipPostService {
    private final TipPostRepository tipPostRepository;

    public List<TipPostDTO> getTipPost(String title, Pageable pageable) {
        Page<Posts> posts;
        if(title==null) {
            log.info("Null title = : {}", title);
            posts = tipPostRepository.findByCategoryFalse(pageable);
        } else {
            log.info("Search_title = : {}", title);
            posts = tipPostRepository.findByTitleContainingAndCategoryFalse(title, pageable);
        }

        log.info("posts : {}", posts.toString());

        return posts.stream()
                .map(post -> new TipPostDTO(
                        post.getPostId(),
                        post.getUser().getUserId(),
                        post.isCategory(),
                        post.getTitle(),
                        post.getContent(),
                        post.getRegisterDate(),
                        post.getModifyDate(),
                        post.getLikes(),
                        post.getComments(),
                        List.of()
                        ))
                .collect(Collectors.toList());
    }
}
