package com.yoyomo.domain.application.domain.vo.condition;

import java.util.Arrays;

import com.yoyomo.domain.application.exception.InvalidSortTypeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {
	NAME("name"),
	LATEST("latest"),
	OLDEST("oldest");

	private final String value;

	public static SortType from(String type) {
		String upperCaseType = type.toUpperCase();
		return Arrays.stream(values())
			.filter(sortType -> sortType.name().equals(upperCaseType))
			.findFirst()
			.orElseThrow(InvalidSortTypeException::new);
	}
}
