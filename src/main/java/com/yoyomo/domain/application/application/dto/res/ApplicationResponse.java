package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.AssessmentStatus;
import com.yoyomo.domain.user.application.dto.res.ApplicantResponse;

import java.time.LocalDate;

public record ApplicationResponse(
        String id,
        ApplicantResponse applicant,
        AssessmentStatus assessmentStatus,
        int currentStage,
        LocalDate createdAt
) {
}
