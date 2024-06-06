package com.yoyomo.domain.recruitment.application.dto.res;

import com.yoyomo.domain.recruitment.domain.entity.recruitmentStatus;

import java.time.LocalDate;

public record RecruitmentResponse(
        String id,
        String title,
        int generation,
        Boolean isRecruitmentActive,
        recruitmentStatus recruitmentStatus,

        LocalDate recruitmentEndDate,
        int totalApplicantsCount,
        int acceptedApplicantsCount,
        int rejectedApplicantsCount
) {
}
