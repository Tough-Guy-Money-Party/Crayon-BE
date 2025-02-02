package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProcessResultRepository extends JpaRepository<ProcessResult, Long> {

    @Query("""
            SELECT pr
            FROM ProcessResult pr
            WHERE pr.processId = :processId AND pr.applicationId IN :applicationIds
            ORDER BY
                CASE WHEN pr.status = 'PENDING' THEN 0 ELSE 1 END ASC,
                pr.id DESC
            """)
    List<ProcessResult> findAllByProcessId(@Param("processId") long processId, @Param("applicationIds") List<UUID> applicationIds);

    Optional<ProcessResult> findByApplicationIdAndProcessId(UUID applicationId, Long processId);

    List<ProcessResult> findAllByProcessIdAndStatus(long processId, Status status);

    List<ProcessResult> findAllByApplicationIdAndProcessIdIsLessThanEqual(UUID applicationId, long processId);
}
