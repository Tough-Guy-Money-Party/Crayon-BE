package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRecordRepository extends JpaRepository<InterviewRecord, Long> {
}
