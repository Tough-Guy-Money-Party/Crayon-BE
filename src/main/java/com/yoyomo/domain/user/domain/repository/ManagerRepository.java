package com.yoyomo.domain.user.domain.repository;

import com.yoyomo.domain.user.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Boolean existsByEmail(String email);

    Optional<Manager> findByEmail(String email);

    Optional<Manager> findByRefreshToken(String token);
}

