package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.service.LifePostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/post/life")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LifePostController {
    public final LifePostService lifePostService;

    /**
     * Life 게시글 불러오기 및 검색
     * @param page
     * @param size
     * @param sort
     * @param title
     * @return
     */
    @GetMapping
    public ResponseEntity getLifePost(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(defaultValue = "registerDate") String sort,
                                     @RequestParam(required = false) String title) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        return ResponseEntity.ok(lifePostService.getLifePost(title, pageable));
    }
}
