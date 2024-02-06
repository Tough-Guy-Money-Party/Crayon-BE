package com.yoyomo.domain.user.domain.repository;

import com.yoyomo.domain.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Boolean existsByEmail(String email);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByEmail(String email);
}

