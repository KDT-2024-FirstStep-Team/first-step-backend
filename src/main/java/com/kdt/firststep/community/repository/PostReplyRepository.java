package com.kdt.firststep.community.repository;

import com.kdt.firststep.community.domain.Replies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReplyRepository extends JpaRepository<Replies, Integer> {
}
