package com.yoyomo.domain.recruitment.application.dto.req;

import com.yoyomo.domain.recruitment.domain.entity.Process;

import java.util.List;

public record RecruitmentRequest(
        String title,
        Integer generation,
        String position,
        List<Process> processes
) {

    public Integer getProcessCount() {
        return processes.size();
    }
}
