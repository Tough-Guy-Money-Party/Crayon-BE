package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.dto.ProcessWithApplicantCount;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    Optional<Process> findByRecruitmentAndStage(Recruitment recruitment, Integer stage);

    List<Process> findAllByRecruitment(Recruitment recruitment);

    void deleteAllByRecruitment(Recruitment recruitment);

    @Query("""
             SELECT new com.yoyomo.domain.recruitment.domain.dto.ProcessWithApplicantCount(
                   p,
                   COUNT(a.id)
             )
             FROM Process p
             LEFT JOIN
                  Application a ON p.id = a.process.id AND a.deletedAt IS NULL
             WHERE p.recruitment.id = :recruitmentId
             GROUP BY p.stage, p.id
             ORDER BY p.stage
            """)
    List<ProcessWithApplicantCount> findAllWithApplicantCount(UUID recruitmentId);
}
