package com.kdt.firststep.user.repository;

import com.kdt.firststep.community.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long> {

    // post 테이블에서 좋아요 수(likes) 순으로 상위 3개의 게시글의 제목과 내용 일부만 가져오기.
    @Query("SELECT p.postId, p.title, SUBSTRING(p.content, 1, 30) AS content " +
        "FROM Posts p " +
        "ORDER BY p.likes DESC")
    List<Object[]> findTop3ByLikesWithTitleAndContent();

    /*
     * {
     * [postId: "1"
     * title: "최신 글 순으로 가져온 첫 번째 게시글 "],
     * [ postId: "2"
     * title: "최신 글 순으로 가져온 첫 번째 게시글 "]
     * }
     *
     * */
    // post 테이블에서 category가 false인 것 중에서 최신 등록일(registerDate) 순으로 상위 2개의 게시글 가져오기
    List<Posts> findTop2ByCategoryFalseOrderByRegisterDateDesc();

    // post 테이블에서 category가 false인 것 중에서 좋아요 수(likes) 순으로 상위 2개의 게시글 가져오기
    // category가 false인 것 중에서 좋아요 수(likes) 순으로 상위 2개의 게시글 가져오기
    List<Posts> findTop2ByCategoryFalseOrderByLikesDesc();
}


