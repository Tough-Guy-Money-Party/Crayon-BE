package com.yoyomo.domain.recruitment.application.dto.req;

import com.yoyomo.domain.recruitment.domain.entity.Process;

import java.util.List;

public record RecruitmentModifyRequest(
        String clubId,
        String formId,
        String title,
        Integer generation,
        String position,
        Boolean isRecruitmentActive,
        List<Process> processes
) {
    public RecruitmentModifyRequest withClubId(String newClubId) {
        return new RecruitmentModifyRequest(
                newClubId,
                this.formId,
                this.title,
                this.generation,
                this.position,
                this.isRecruitmentActive,
                this.processes
        );
    }
}
