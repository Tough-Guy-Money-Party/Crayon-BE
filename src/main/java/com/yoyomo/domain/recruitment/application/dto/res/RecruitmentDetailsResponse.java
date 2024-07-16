package com.yoyomo.domain.recruitment.application.dto.res;

import com.yoyomo.domain.recruitment.domain.entity.Process;

import java.util.List;

public record RecruitmentDetailsResponse(
        String title,
        String generation,
        String position,
        List<Process> processes,
        int processCount
) {
}
