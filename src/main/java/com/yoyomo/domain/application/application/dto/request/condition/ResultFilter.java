package com.yoyomo.domain.application.application.dto.request.condition;

import java.util.Arrays;

public enum ResultFilter {
	ALL,
	NONE,
	PENDING,
	PASS,
	FAIL,
	;

	public static ResultFilter from(String filter) {
		String upperCaseFilter = filter.toUpperCase();
		return Arrays.stream(values())
			.filter(sortType -> sortType.name().equals(upperCaseFilter))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);
	}

	public boolean isWithoutResult() {
		return this == NONE;
	}

	public boolean isAll() {
		return this == ALL;
	}
}
