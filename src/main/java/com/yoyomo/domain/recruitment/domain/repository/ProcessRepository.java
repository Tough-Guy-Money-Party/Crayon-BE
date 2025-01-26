package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    Optional<Process> findByRecruitmentAndStage(Recruitment recruitment, Integer stage);

    List<Process> findAllByRecruitment(Recruitment recruitment);

    void deleteAllByRecruitment(Recruitment recruitment);
}
