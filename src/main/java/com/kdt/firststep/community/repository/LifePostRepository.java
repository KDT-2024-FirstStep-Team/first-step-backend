package com.kdt.firststep.community.repository;

import com.kdt.firststep.community.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifePostRepository extends JpaRepository<Posts, Integer> {
    Page<Posts> findByCategoryTrue(Pageable pageable);
    Page<Posts> findByTitleContainingAndCategoryTrue(String title, Pageable pageable);
} 