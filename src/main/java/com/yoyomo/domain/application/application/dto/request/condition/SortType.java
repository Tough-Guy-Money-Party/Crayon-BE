package com.yoyomo.domain.application.application.dto.request.condition;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {
	NAME("name"),
	APPLIED("applied"),
	;
	private final String value;

	public static SortType from(String type) {
		String upperCaseType = type.toUpperCase();
		return Arrays.stream(values())
			.filter(sortType -> sortType.name().equals(upperCaseType))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);
	}
}
