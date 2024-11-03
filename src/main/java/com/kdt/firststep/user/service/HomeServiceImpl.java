package com.kdt.firststep.user.service;


import com.kdt.firststep.community.domain.Post;
import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.user.dto.response.BestPostResponseDTO;
import com.kdt.firststep.user.dto.response.CounselorContentSummaryResponseDTO;
import com.kdt.firststep.user.repository.PostRepository;
import com.kdt.firststep.user.repository.CounselorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService{

    private final CounselorRepository counselorRepository;
    private final PostRepository postRepository;

    @Autowired
    public HomeServiceImpl(CounselorRepository counselorRepository, PostRepository communityRepository) {
        this.counselorRepository = counselorRepository;
        this.postRepository = communityRepository;
    }

    // Top 5 베스트 상담사 가져오기(리뷰순) | /home/top5Counselor
    @Override
    public List<CounselorProfile> getTop5CounselorsByReviews() {
        return counselorRepository.findTop5CounselorsByAverageRating();
    }

    // /home/BestCommunity(일단은 5개만?)
    // 필요한 거 : category, title, content -> 일단 다 String
    @Override
    public List<BestPostResponseDTO> getBestPostByLikes() {
        List<Object[]> results = postRepository.findTop3ByLikesWithTitleAndContent();

        // DTO로 변환
        return results.stream()
                    .map(result -> new BestPostResponseDTO(
                        ((Number) result[0]).longValue(),  // postId
                        (String) result[1],               // title
                        (String) result[2]                // content (substring)
                    ))
                .collect(Collectors.toList());
    }

    // 상담사 콘텐츠 불러오기 (제목, postId, 좋아요 순 2개, 최신순 2개)
    @Override
    public List<CounselorContentSummaryResponseDTO> getCounselorContentByLikesAndRecent() {
        List<Post> topLikes = postRepository.findTop2ByCategoryFalseOrderByLikesDesc();
        List<Post> topRecent = postRepository.findTop2ByCategoryFalseOrderByRegisterDateDesc();

        List<Post> combined = new ArrayList<>();
        combined.addAll(topLikes);
        combined.addAll(topRecent);

        // Post 리스트를 CounselorContentResponseDTO 리스트로 변환
        return combined.stream()
            .map(post -> new CounselorContentSummaryResponseDTO(
                post.getPostId(),
                post.getTitle(),
                post.getWriter().getUserName(),  // 작성자의 이름
                post.getLikes()
            ))
            .collect(Collectors.toList());
    }
}
