package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.entity.enums.Rating;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record EvaluationResponses(
        EvaluationResponse evaluationResponse,
        List<EvaluationMemoResponse> memoResponses
) {
    public static EvaluationResponses toResponse(Application application, Evaluation myEvaluation, List<Evaluation> evaluations, List<EvaluationMemo> memos) {
        Map<Rating, Long> ratingCount = evaluations.stream()
                .collect(Collectors.groupingBy(Evaluation::getRating, Collectors.counting()));
        EvaluationResponse evaluationResponse = EvaluationResponse.toResponse(application, myEvaluation, ratingCount);

        List<EvaluationMemoResponse> memoResponses = memos.stream()
                .map(EvaluationMemoResponse::toResponse)
                .toList();
        return new EvaluationResponses(evaluationResponse, memoResponses);
    }
}
