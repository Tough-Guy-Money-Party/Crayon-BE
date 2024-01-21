package com.yoyomo.domain.recruitment.application.dto.req;

import com.yoyomo.domain.recruitment.domain.entity.Schedule;

public record RecruitmentRequest(
        String name,
        int generation,
        Schedule schedule
) {
}
