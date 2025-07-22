package com.yoyomo.domain.item.application.dto.req;

import java.util.List;

import com.yoyomo.domain.item.domain.entity.type.Type;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ItemRequest(
	@NotEmpty String title,
	@NotNull Type type,
	String description,
	@NotNull Integer order,
	@NotNull Boolean required,

	// for Select
	List<OptionRequest> options,

	// for Answer & Date
	String answer,
	Integer maxLength,

	// for Score
	String meaningOfHigh,
	String meaningOfLow,
	Integer score
) {
}

