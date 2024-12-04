package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import lombok.Builder;

import java.util.Map;

@Builder
public record EvaluationResponse(
        Status status,
        Long myEvaluationId,
        Rating myRating,
        RatingResponse ratingResponse
) {
    public static EvaluationResponse toResponse(Application application, Evaluation myEvaluation, Map<Rating, Long> ratingCount) {
        RatingResponse ratingResponses = RatingResponse.toResponse(ratingCount);
        return EvaluationResponse.builder()
                .status(application.getStatus())
                .myEvaluationId(myEvaluation.getId())
                .myRating(myEvaluation.getRating())
                .ratingResponse(ratingResponses)
                .build();
    }
}
