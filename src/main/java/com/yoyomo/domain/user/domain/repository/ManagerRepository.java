package com.yoyomo.domain.user.domain.repository;

import com.yoyomo.domain.user.domain.entity.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends MongoRepository<Manager, String> {
    Boolean existsByEmail(String email);

    Optional<Manager> findByEmail(String email);
}

