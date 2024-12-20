package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.dto.ReplyDTO;
import com.kdt.firststep.community.service.LifePostReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/life/reply")
@Slf4j
public class LifePostReplyController {
    private final LifePostReplyService lifePostReplyService;

    @PostMapping
    public ResponseEntity postReply(@RequestBody ReplyDTO replyDTO) {
        lifePostReplyService.postReply(replyDTO);
        log.info("생활 게시글 답글 저장 완료");
        return ResponseEntity.ok().build();
    }
} 