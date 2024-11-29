package com.yoyomo.domain.user.domain.repository;

import com.yoyomo.domain.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByRefreshToken(String token);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);
}

