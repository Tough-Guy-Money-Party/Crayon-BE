package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.user.application.dto.res.ApplicantResponse;

import java.time.LocalDate;

public record ApplicationResponse(
        String id,
        ApplicantResponse applicant,
        int currentStage,
        LocalDate createdAt
) {
}
