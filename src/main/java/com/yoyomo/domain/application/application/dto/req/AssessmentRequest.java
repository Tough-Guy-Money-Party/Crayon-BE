package com.yoyomo.domain.application.application.dto.req;

import com.yoyomo.domain.application.domain.entity.AssessmentRating;
import com.yoyomo.domain.application.domain.entity.AssessmentStatus;
import jakarta.validation.constraints.NotBlank;

public record AssessmentRequest(
        String managerId,

        String managerName,

        AssessmentRating assessmentRating,

        AssessmentStatus assessmentStatus,

        String assessmentText
) {
}
