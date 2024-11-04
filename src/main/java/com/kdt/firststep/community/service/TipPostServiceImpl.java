package com.kdt.firststep.community.service;

import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.community.dto.PostDTO;
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

    public List<PostDTO> getTipPost(Pageable pageable) {
        Page<Posts> posts = tipPostRepository.findByCategoryFalse(pageable);
        log.info("posts : {}", posts.toString());
        return posts.stream()
                .map(post -> new PostDTO(
                        post.getPostId(),
                        post.getUser().getUserId(),
                        post.isCategory(),
                        post.getTitle(),
                        post.getContent(),
                        post.getRegisterDate(),
                        post.getModifyDate(),
                        post.getLikes(),
                        post.getComments()))
                .collect(Collectors.toList());
    }
}
