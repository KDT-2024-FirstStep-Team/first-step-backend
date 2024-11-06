package com.kdt.firststep.community.controller;

import com.kdt.firststep.community.dto.ReplyDTO;
import com.kdt.firststep.community.service.PostReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/comment")
@Slf4j
public class PostReplyController {
    private final PostReplyService postReplyService;

    @PostMapping
    public ResponseEntity postReply(@RequestBody ReplyDTO replyDTO) {
        postReplyService.postReply(replyDTO);
        log.info("답글 저장 완료");
        return ResponseEntity.ok().build();
    }

}
