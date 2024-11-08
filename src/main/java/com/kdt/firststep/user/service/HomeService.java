package com.kdt.firststep.user.service;

import com.kdt.firststep.user.dto.response.BestPostResponseDTO;
import com.kdt.firststep.user.dto.response.CounselorContentSummaryResponseDTO;
import com.kdt.firststep.user.dto.response.CounselorProfileWithRatingResponseDTO;
import java.util.List;

public interface HomeService {
    //  CounselorRespoitory
    // Top 5 베스트 상담사 가져오기(리뷰순) | /home/top5Counselor
    List<CounselorProfileWithRatingResponseDTO> getTop5CounselorsByReviews();

    // PostRepository
    // Top 3 베스트 커뮤니티 글 가져오기(좋아요순)
    List<BestPostResponseDTO> getBestPostByLikes();

    // 상담사 콘텐츠 불러오기(제목, postId, 좋아요순 2개 최신순 2개) | /home/counselorContent
    List<CounselorContentSummaryResponseDTO> getCounselorContentByLikesAndRecent();
}
