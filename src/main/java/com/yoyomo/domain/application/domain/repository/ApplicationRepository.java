package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.dto.ProcessApplicant;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findAllByUserAndDeletedAtIsNull(User user);

    List<Application> findByProcessIdAndDeletedAtIsNull(Long processId, Pageable pageable);

    Page<Application> findAllByUserNameContainingAndProcessAndDeletedAtIsNull(String userName, Process process,
                                                                              Pageable pageable);

    Optional<Application> findByIdAndDeletedAtIsNull(UUID id);

    Optional<Application> findByRecruitmentIdAndUser(UUID recruitment, User applicant);

    @Query("""
            SELECT a 
            FROM Application a
            WHERE a.process = :process AND a.deletedAt IS NULL
            ORDER BY 
                CASE WHEN a.status = 'PENDING' THEN 0 ELSE 1 END ASC, 
                a.createdAt DESC
            """)
    Page<Application> findAllByProcessOrderByPending(@Param("process") Process process,
                                                     Pageable pageable);

    @Query("""
            SELECT a 
            FROM Application a
            WHERE a.process = :process AND a.deletedAt IS NULL
            ORDER BY 
                CASE WHEN a.status = 'PENDING' THEN 0 ELSE 1 END ASC, 
                a.createdAt DESC
            """)
    List<Application> findAllByProcessOrderByPending(@Param("process") Process process);


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
    @Query("UPDATE Application a SET a.process = :process, a.status = :status WHERE a.id IN :applicationIds")
    void updateProcess(@Param("applicationIds") List<UUID> applicationIds,
                       @Param("process") Process process,
                       @Param("status") Status status);

    @Modifying
    @Query("DELETE FROM Application a WHERE a.recruitmentId = :recruitmentId")
    void deleteAllByRecruitmentId(UUID recruitmentId);
    
}
