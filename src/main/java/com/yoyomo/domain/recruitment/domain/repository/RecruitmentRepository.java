package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecruitmentRepository extends MongoRepository<Recruitment, String> {
    Optional<Recruitment> findByIdAndDeletedAtIsNull(String id);

    Page<Recruitment> findAllByClubIdAndDeletedAtIsNull(String clubId, PageRequest pageRequest);

    List<Recruitment> findAllByClubId(String clubId);

}
