package com.kdt.firststep.counselor.repository;

import com.kdt.firststep.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);  // 기본 제공되는 메서드라 실제로는 선언 불필요
}
