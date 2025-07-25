package com.yoyomo.domain.application.domain.vo.condition;

import java.util.Arrays;

import com.yoyomo.domain.application.exception.InvalidFilterTypeException;

public enum EvaluationFilter {
	ALL,
	YES,
	NO;

	public static EvaluationFilter from(String filter) {
		String upperCaseFilter = filter.toUpperCase();
		return Arrays.stream(values())
			.filter(sortType -> sortType.name().equals(upperCaseFilter))
			.findFirst()
			.orElseThrow(InvalidFilterTypeException::new);
	}

	public boolean isAll() {
		return this == ALL;
	}
}
