package com.kdt.firststep.user.controller;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.user.dto.response.BestPostResponseDTO;
import com.kdt.firststep.user.dto.response.CounselorContentSummaryResponseDTO;
import com.kdt.firststep.user.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;


    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    // Top 5 베스트 상담사 가져오기 API
    //  CounselorProfile 엔티티에서 직접
    @GetMapping("/top5Counselor")
    public ResponseEntity<List<CounselorProfile>> getTop5CounselorByReviews() {
        List<CounselorProfile> top5Counselors = homeService.getTop5CounselorsByReviews();
        return ResponseEntity.ok(top5Counselors);
    }

    // 베스트 게시글 3개 가져오기
    // BestPostResponseDTO를 통해서 가져옴.
    @GetMapping("/bestPost")
    public ResponseEntity<List<BestPostResponseDTO>> getBestPosts() {
        List<BestPostResponseDTO> bestPosts = homeService.getBestPostByLikes();
        return ResponseEntity.ok(bestPosts);
    }


    //상담사 콘텐츠 불러오기 API (상담사 콘텐츠 불러오기(제목, postId, 좋아요순 2개 최신순 2개))
    @GetMapping("/counselorContent")
    public ResponseEntity<List<CounselorContentSummaryResponseDTO>> getCounselorContents() {
        List<CounselorContentSummaryResponseDTO> content = homeService.getCounselorContentByLikesAndRecent();
        return ResponseEntity.ok(content);
    }
}
