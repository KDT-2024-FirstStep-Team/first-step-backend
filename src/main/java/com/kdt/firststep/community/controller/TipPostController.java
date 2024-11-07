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
@RequestMapping("api/v1/post/tip")
public class TipPostController {
    private final TipPostService tipPostService;

    @GetMapping
    public ResponseEntity getTipPost(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "3") Integer size,
                                     @RequestParam(defaultValue = "registerDate") String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());  // 페이지네이션 적용) {
        return ResponseEntity.ok(tipPostService.getTipPost(null, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity getTipPostSearch(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "3") Integer size,
                                           @RequestParam String title){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "registerDate");
        return ResponseEntity.ok(tipPostService.getTipPost(title, pageable));
    }
}
