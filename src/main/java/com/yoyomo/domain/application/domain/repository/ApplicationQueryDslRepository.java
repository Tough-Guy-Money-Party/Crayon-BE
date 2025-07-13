package com.yoyomo.domain.application.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yoyomo.domain.application.application.usecase.dto.ApplicationCondition;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
import com.yoyomo.domain.recruitment.domain.entity.Process;

public interface ApplicationQueryDslRepository {

	Page<ApplicationWithStatus> findAllWithStatusByProcess(
		Process process, ApplicationCondition condition, Pageable pageable
	);
}
