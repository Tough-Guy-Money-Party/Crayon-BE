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
                   (SELECT COUNT (a.id)
                    FROM Application a
                    WHERE a.process.id = p.id
                    AND a.deletedAt IS NULL)
             )
             FROM Process p
             WHERE p.recruitment.id = :recruitmentId
             ORDER BY p.stage
            """)
    List<ProcessWithApplicantCount> findAllWithApplicantCount(UUID recruitmentId);
}
