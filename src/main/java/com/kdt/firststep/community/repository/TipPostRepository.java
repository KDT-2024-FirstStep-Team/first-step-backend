package com.kdt.firststep.community.repository;


import com.kdt.firststep.community.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipPostRepository extends JpaRepository<Posts, Integer> {
    Page<Posts> findByCategoryFalse(Pageable pageable);
    Page<Posts> findByTitleContainingAndCategoryFalse(String title, Pageable pageable);
}
