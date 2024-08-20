package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecruitmentRepository extends JpaRepository<Recruitment, UUID> {

    Page<Recruitment> findAllByIsActiveIsTrue(Pageable pageable);
}
