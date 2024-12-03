package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record EvaluationResponses(
        EvaluationResponse evaluationResponse,
        List<EvaluationMemoResponse> memoResponses
) {
    public static EvaluationResponses toResponse(Application application, List<Evaluation> evaluations, List<EvaluationMemo> memos) {
        Map<String, Long> ratingCount = evaluations.stream()
                .collect(Collectors.groupingBy(evaluation -> evaluation.getRating().toString(), Collectors.counting()));
        EvaluationResponse evaluationResponse = EvaluationResponse.toResponse(application, ratingCount);

        List<EvaluationMemoResponse> memoResponses = memos.stream()
                .map(EvaluationMemoResponse::toResponse)
                .toList();
        return new EvaluationResponses(evaluationResponse, memoResponses);
    }
}
