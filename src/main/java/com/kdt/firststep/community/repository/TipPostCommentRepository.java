package com.kdt.firststep.community.repository;

import com.kdt.firststep.community.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipPostCommentRepository extends JpaRepository<Comments, Integer> {
    List<Comments> findByPost_PostId(int postId);
}
