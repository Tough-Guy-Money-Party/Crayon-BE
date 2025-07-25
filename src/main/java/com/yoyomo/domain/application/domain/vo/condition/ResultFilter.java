package com.yoyomo.domain.application.domain.vo.condition;

import java.util.Arrays;

import com.yoyomo.domain.application.exception.InvalidFilterTypeException;

public enum ResultFilter {
	ALL,
	NONE,
	PENDING,
	PASS,
	FAIL;

	public static ResultFilter from(String filter) {
		String upperCaseFilter = filter.toUpperCase();
		return Arrays.stream(values())
			.filter(sortType -> sortType.name().equals(upperCaseFilter))
			.findFirst()
			.orElseThrow(InvalidFilterTypeException::new);
	}

	public boolean isWithoutResult() {
		return this == NONE;
	}

	public boolean isAll() {
		return this == ALL;
	}
}
