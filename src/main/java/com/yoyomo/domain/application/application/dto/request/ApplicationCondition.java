package com.yoyomo.domain.application.application.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.yoyomo.domain.application.application.dto.request.condition.EvaluationFilter;
import com.yoyomo.domain.application.application.dto.request.condition.ResultFilter;
import com.yoyomo.domain.application.application.dto.request.condition.SortType;

import lombok.Builder;

@Builder
public record ApplicationCondition(
	SortType sortType,
	EvaluationFilter evaluationFilter,
	ResultFilter resultFilter,
	PageRequest pageRequest
) {
	public PageRequest toPageRequest(Sort sort) {
		return pageRequest.withSort(sort);
	}
}
