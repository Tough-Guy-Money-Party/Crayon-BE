package com.yoyomo.domain.application.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.user.domain.entity.User;

public interface InterviewRecordRepository extends JpaRepository<InterviewRecord, Long> {
	boolean existsByManagerAndApplicationId(User manager, UUID applicationId);

	@EntityGraph(attributePaths = "manager")
	List<InterviewRecord> findAllByApplicationIdOrderByCreatedAtDesc(UUID applicationId);
}
