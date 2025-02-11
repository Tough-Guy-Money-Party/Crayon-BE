package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record EvaluationResponses(
        EvaluationResponse evaluationResponse,
        List<EvaluationMemoResponse> memoResponses
) {
    public static EvaluationResponses toResponse(
            List<ProcessResult> processResult,
            Evaluation myEvaluation,
            List<Evaluation> evaluations,
            List<EvaluationMemo> memos,
            User manager
    ) {
        Map<Rating, Long> ratingCount = evaluations.stream()
                .collect(Collectors.groupingBy(Evaluation::getRating, Collectors.counting()));
        EvaluationResponse evaluationResponse = EvaluationResponse.toResponse(processResult, myEvaluation, ratingCount);

        List<EvaluationMemoResponse> memoResponses = memos.stream()
                .map(memo -> EvaluationMemoResponse.toResponse(memo, manager))
                .toList();
        return new EvaluationResponses(evaluationResponse, memoResponses);
    }
}
