package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecruitmentRepository extends MongoRepository<Recruitment, String> {
}
