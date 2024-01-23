package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitmentRepository extends MongoRepository<Recruitment, String> {
    Optional<Recruitment> findByIdAndDeletedAtIsNull(String id);

    List<Recruitment> findAllByClubIdAndDeletedAtIsNull(String clubId);
}
