package com.yoyomo.domain.application.application.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record StageUpdateRequest(
	@NotNull List<String> ids,
	@NotNull Integer from,
	@NotNull Integer to
) {
}
