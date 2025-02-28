package com.yoyomo.domain.recruitment.domain.dto;

import com.yoyomo.domain.recruitment.domain.entity.Process;

public record ProcessWithApplicantCount(
        Process process,
        long applicantCount
) {
}
