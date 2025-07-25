package com.yoyomo.domain.application.application.dto.response;

import java.util.List;
import java.util.Map;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;

import lombok.Builder;

@Builder
public record EvaluationResponse(
	List<StatusResponse> status,
	Long myEvaluationId,
	Rating myRating,
	RatingResponse ratingResponse
) {

	public static EvaluationResponse toResponse(
		List<ProcessResult> processResults,
		Evaluation myEvaluation,
		Map<Rating, Long> ratingCount
	) {
		RatingResponse ratingResponses = RatingResponse.toResponse(ratingCount);
		List<StatusResponse> statusResponses = processResults.stream()
			.map(StatusResponse::toResponse)
			.toList();
		return EvaluationResponse.builder()
			.status(statusResponses)
			.myEvaluationId(myEvaluation.getId())
			.myRating(myEvaluation.getRating())
			.ratingResponse(ratingResponses)
			.build();
	}

	private record StatusResponse(
		long processId,
		Status status
	) {

		private static StatusResponse toResponse(ProcessResult processResult) {
			return new StatusResponse(processResult.getProcessId(), processResult.getStatus());
		}
	}
}
