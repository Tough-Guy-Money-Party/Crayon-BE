package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.user.application.dto.res.ApplicantResponse;

public record ApplicationResponse(
        String id,
        ApplicantResponse applicant,
        int applicationStage
) {
}
