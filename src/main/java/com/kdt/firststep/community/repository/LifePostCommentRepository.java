package com.kdt.firststep.community.repository;

import com.kdt.firststep.community.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifePostCommentRepository extends JpaRepository<Comments, Integer> {
    @Query("SELECT c FROM Comments c " +
            "LEFT JOIN FETCH c.repliesList r " +
            "LEFT JOIN FETCH c.user " +
            "WHERE c.post.postId = :postId")
    List<Comments> findLifeComment(@Param("postId") int postId);
} 