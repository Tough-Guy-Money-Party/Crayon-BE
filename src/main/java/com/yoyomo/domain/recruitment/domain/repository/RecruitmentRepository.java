package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecruitmentRepository extends MongoRepository<Recruitment, String> {
    List<Recruitment> findAllByFormId(String formId);
}
