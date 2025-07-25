package com.yoyomo.domain.user.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoyomo.domain.user.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Boolean existsByEmail(String email);

	Optional<User> findByEmailAndDeletedAtIsNull(String email);

	Optional<User> findByRefreshToken(String token);

	Optional<User> findByIdAndDeletedAtIsNull(long id);
}

