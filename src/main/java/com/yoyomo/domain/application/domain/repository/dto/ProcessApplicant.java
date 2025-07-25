package com.yoyomo.domain.application.domain.repository.dto;

import com.yoyomo.domain.recruitment.domain.entity.Process;

public record ProcessApplicant(
	Process process,
	long applicantCount
) {
}
