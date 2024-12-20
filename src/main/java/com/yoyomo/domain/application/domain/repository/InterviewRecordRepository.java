package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InterviewRecordRepository extends JpaRepository<InterviewRecord, Long> {
    boolean existsByManagerAndApplicationId(User manager, UUID applicationId);

    List<InterviewRecord> findAllByApplicationIdOrderByCreatedAtDesc(UUID applicationId);
}
