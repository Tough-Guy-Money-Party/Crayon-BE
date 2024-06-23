package com.yoyomo.domain.recruitment.application.dto.req;

import com.yoyomo.domain.recruitment.domain.entity.Process;

import java.util.List;

public record RecruitmentRequest(
        String clubId,
        String formId,
        String title,
        Integer generation,
        String position,

        List<Process> processes
) {
    public RecruitmentRequest withClubId(String newClubId) {
        return new RecruitmentRequest(
                newClubId,
                this.formId,
                this.title,
                this.generation,
                this.position,
                this.processes
        );
    }
}
