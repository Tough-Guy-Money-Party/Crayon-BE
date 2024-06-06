package com.yoyomo.domain.recruitment.application.dto.req;

import com.yoyomo.domain.recruitment.domain.entity.Process;

import java.util.List;

public record RecruitmentRequest(
        String clubId,
        String formId,
        String title,
        Integer generation,
        String position,

        int processStage,
        List<Process> processes
) {
}
