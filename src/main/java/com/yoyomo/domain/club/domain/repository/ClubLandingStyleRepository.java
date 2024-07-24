package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.ClubLandingStyle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubLandingStyleRepository extends MongoRepository<ClubLandingStyle, String> {
}
