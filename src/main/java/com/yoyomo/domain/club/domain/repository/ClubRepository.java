package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClubRepository extends MongoRepository<Club, String> {
    Optional<Club> findByIdAndDeletedAtIsNull(String id);
}
