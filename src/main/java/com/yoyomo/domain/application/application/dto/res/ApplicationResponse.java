package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.ApplicationStatus;
import com.yoyomo.domain.user.application.dto.res.ApplicantResponse;

public record ApplicationResponse(
        String id,
        ApplicantResponse applicant,
        ApplicationStatus applicationStatus
) {
}
