package com.kdt.firststep.community.repository;

import com.kdt.firststep.community.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipPostCommentRepository extends JpaRepository<Comments, Integer> {
    /**
     * Comments 엔티티에서 Post 객체의 postId가 postId와 일치하는 모든 Comments 엔티티를 반환
     * @param postId
     * @return
     */
    List<Comments> findByPost_PostId(int postId);
}
