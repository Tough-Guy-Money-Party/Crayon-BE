package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    Optional<Process> findByRecruitmentAndStage(Recruitment recruitment, Integer stage);

    List<Process> findByRecruitment(Recruitment recruitment);
}
