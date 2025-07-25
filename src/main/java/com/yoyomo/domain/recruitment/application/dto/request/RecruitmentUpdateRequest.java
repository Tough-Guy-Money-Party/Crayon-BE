package com.yoyomo.domain.recruitment.application.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record RecruitmentUpdateRequest(

	@NotNull
	String title,

	@NotNull
	String position,

	@NotNull
	LocalDateTime startAt,

	@NotNull
	LocalDateTime endAt
) {
}
