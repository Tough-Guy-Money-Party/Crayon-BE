package com.yoyomo.domain.application.application.usecase.dto;

import com.yoyomo.domain.application.domain.vo.condition.EvaluationFilter;
import com.yoyomo.domain.application.domain.vo.condition.ResultFilter;
import com.yoyomo.domain.application.domain.vo.condition.SortType;

import lombok.Builder;

@Builder
public record ApplicationCondition(
	SortType sortType,
	EvaluationFilter evaluationFilter,
	ResultFilter resultFilter
) {
	public static ApplicationCondition from(String sortType, String evaluationFilter, String resultFilter) {
		return ApplicationCondition.builder()
			.sortType(SortType.from(sortType))
			.evaluationFilter(EvaluationFilter.from(evaluationFilter))
			.resultFilter(ResultFilter.from(resultFilter))
			.build();
	}
}
