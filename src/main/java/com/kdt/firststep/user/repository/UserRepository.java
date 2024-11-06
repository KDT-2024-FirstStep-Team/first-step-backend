package com.kdt.firststep.user.repository;

import com.kdt.firststep.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
}
