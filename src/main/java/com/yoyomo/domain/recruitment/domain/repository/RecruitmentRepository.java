package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecruitmentRepository extends JpaRepository<Recruitment, UUID> {

    Optional<Recruitment> findByIdAndDeletedAtIsNull(UUID id);
}
