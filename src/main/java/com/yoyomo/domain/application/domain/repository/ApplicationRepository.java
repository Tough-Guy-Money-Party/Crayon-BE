package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
import com.yoyomo.domain.application.domain.repository.dto.ProcessApplicant;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findAllByUserAndDeletedAtIsNull(User user);

    List<Application> findByProcessIdAndDeletedAtIsNull(Long processId, Pageable pageable);

    Page<Application> findAllByUserNameContainingAndProcessAndDeletedAtIsNull(String userName, Process process,
                                                                              Pageable pageable);

    Optional<Application> findByIdAndDeletedAtIsNull(UUID id);

    Optional<Application> findByRecruitmentIdAndUser(UUID recruitment, User applicant);

    List<Application> findAllByProcessOrderByUserName(Process process);

    @Query("""
            SELECT new com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus(
                a,
                COALESCE(pr.status, 'BEFORE_EVALUATION')
            )
            FROM Application a
            LEFT JOIN ProcessResult pr ON a.id = pr.applicationId
            WHERE a.process = :process AND a.deletedAt IS NULL
            """)
    List<ApplicationWithStatus> findAllWithStatusByProcess(@Param("process") Process process);

    @Query("""
             SELECT new com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus(
                 a,
                 pr.status
             )
             FROM Application a
             LEFT JOIN ProcessResult pr ON a.id = pr.applicationId AND a.process.id = pr.processId
             WHERE a.process = :process AND a.deletedAt IS NULL
             ORDER BY CASE
                     WHEN pr.status = 'PENDING'
                     THEN 0 ELSE 1 END,
                     a.createdAt DESC
            """)
    Page<ApplicationWithStatus> findAllWithStatusByProcess(@Param("process") Process process, Pageable pageable);

    @Query("""
            SELECT new com.yoyomo.domain.application.domain.repository.dto.ProcessApplicant(
                a.process,
                COUNT(a.id)
            )
            FROM Application a
            WHERE a.recruitmentId = :recruitmentId AND a.process IN :processes AND a.deletedAt IS NULL 
            GROUP BY a.process
            """)
    List<ProcessApplicant> countByRecruitmentAndProcess(UUID recruitmentId, List<Process> processes);

    @Modifying
    @Query("UPDATE Application a SET a.process = :process WHERE a.id IN :applicationIds")
    void updateProcess(@Param("applicationIds") List<UUID> applicationIds,
                       @Param("process") Process process);

    @Modifying
    @Query("DELETE FROM Application a WHERE a.recruitmentId = :recruitmentId")
    void deleteAllByRecruitmentId(UUID recruitmentId);
}
