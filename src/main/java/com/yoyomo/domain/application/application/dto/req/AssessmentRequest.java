package com.yoyomo.domain.application.application.dto.req;

import com.yoyomo.domain.application.domain.entity.AssessmentRating;
import com.yoyomo.domain.application.domain.entity.AssessmentStatus;
import jakarta.validation.constraints.NotNull;

public record AssessmentRequest(
        @NotNull AssessmentRating assessmentRating,
        @NotNull AssessmentStatus assessmentStatus,
        String assessmentText
) {
}
