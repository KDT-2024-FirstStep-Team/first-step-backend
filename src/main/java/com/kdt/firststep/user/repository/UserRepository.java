package com.kdt.firststep.user.repository;

import com.kdt.firststep.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String userEmail);

    Optional<Users> findByNickname(String nickname);
}
