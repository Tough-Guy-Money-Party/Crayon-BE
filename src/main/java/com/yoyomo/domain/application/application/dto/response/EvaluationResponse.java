package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import lombok.Builder;

import java.util.Map;

@Builder
public record EvaluationResponse(
        Status status,
        Map<String, Long> ratingCount
) {
    public static EvaluationResponse toResponse(Application application, Map<String, Long> ratingCount) {
        return EvaluationResponse.builder()
                .status(application.getStatus())
                .ratingCount(ratingCount)
                .build();
    }
}
