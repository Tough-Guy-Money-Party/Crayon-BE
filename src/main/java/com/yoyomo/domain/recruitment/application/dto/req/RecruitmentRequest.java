package com.yoyomo.domain.recruitment.application.dto.req;

import com.yoyomo.domain.recruitment.domain.entity.Schedule;

import java.util.List;

public record RecruitmentRequest(
        String name,
        int generation,
        List<Schedule> calendar
) {
}
