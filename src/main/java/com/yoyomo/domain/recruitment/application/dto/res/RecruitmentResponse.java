package com.yoyomo.domain.recruitment.application.dto.res;

import com.yoyomo.domain.recruitment.domain.entity.RecruitmentStatus;

import java.time.LocalDate;

public record RecruitmentResponse(
        String id,
        String title,
        int generation,
        Boolean isRecruitmentActive,
        RecruitmentStatus recruitmentStatus,
        LocalDate recruitmentEndDate,
        int totalApplicantsCount,
        int acceptedApplicantsCount,
        int rejectedApplicantsCount
) {
}
