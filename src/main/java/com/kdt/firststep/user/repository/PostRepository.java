package com.kdt.firststep.user.repository;

import com.kdt.firststep.community.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long> {

    // post 테이블에서 좋아요 수(likes) 순으로 상위 3개의 게시글의 제목과 내용 일부만 가져오기.
    @Query(value = "SELECT p.post_id, u.nickname, p.title, SUBSTRING(p.content, 1, 100) AS content, DATE_FORMAT(p.register_date, '%Y-%m-%d') AS registerDate " +
        "FROM posts p " +
         "JOIN users u " +
            "ON p.user_id = u.user_id " +  // users 테이블과 조인
        "WHERE p.category = 0 " +
        "ORDER BY p.likes DESC " +
        "LIMIT 3",
        nativeQuery = true)
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
    // post 테이블에서 category가 True인 것 중에서 최신 등록일(registerDate) 순으로 상위 2개의 게시글 가져오기
    List<Posts> findTop2ByCategoryTrueOrderByRegisterDateDesc();

    // post 테이블에서 category가 false인 것 중에서 좋아요 수(likes) 순으로 상위 2개의 게시글 가져오기
    // category가 false인 것 중에서 좋아요 수(likes) 순으로 상위 2개의 게시글 가져오기
    List<Posts> findTop2ByCategoryTrueOrderByLikesDesc();
}


