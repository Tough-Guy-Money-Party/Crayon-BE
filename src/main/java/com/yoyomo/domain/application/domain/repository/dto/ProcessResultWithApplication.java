package com.yoyomo.domain.application.domain.repository.dto;

import java.util.UUID;

import com.yoyomo.domain.application.domain.entity.enums.Status;

public record ProcessResultWithApplication(
	Status status,
	UUID applicationId
) {

	public ProcessResultWithApplication {
		if (status == null) {
			status = Status.BEFORE_EVALUATION;
		}
	}
}
