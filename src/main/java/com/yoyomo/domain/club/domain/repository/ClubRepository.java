package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepository extends MongoRepository<Club, String> {
}
