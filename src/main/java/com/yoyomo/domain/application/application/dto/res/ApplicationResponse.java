package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Assessment;
import com.yoyomo.domain.application.domain.entity.AssessmentRating;
import com.yoyomo.domain.application.domain.entity.AssessmentStatus;
import com.yoyomo.domain.user.application.dto.res.ApplicantResponse;

import java.time.LocalDate;
import java.util.List;

public record ApplicationResponse(
        String id,
        ApplicantResponse applicant,
        AssessmentStatus assessmentStatus,
        AssessmentRating averageAssessmentRating,
        List<Assessment> assessments,
        int currentStage,
        String currentStageTitle,
        LocalDate createdAt
) {
}
