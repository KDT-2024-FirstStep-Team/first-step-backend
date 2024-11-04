package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.service.TipPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class TipPostController {
    private final TipPostService tipPostService;

    @GetMapping("/tip")
    public ResponseEntity getTipPost(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "3") int size,
                                     @RequestParam(defaultValue = "registerDate") String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());  // 페이지네이션 적용) {
        return ResponseEntity.ok(tipPostService.getTipPost(pageable));
    }
}
