package com.yoyomo.domain.application.domain.vo.condition;

import java.util.Arrays;

public enum EvaluationFilter {
	ALL,
	YES,
	NO,
	;

	public static EvaluationFilter from(String filter) {
		String upperCaseFilter = filter.toUpperCase();
		return Arrays.stream(values())
			.filter(sortType -> sortType.name().equals(upperCaseFilter))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);
	}

	public boolean isAll() {
		return this == ALL;
	}
}
