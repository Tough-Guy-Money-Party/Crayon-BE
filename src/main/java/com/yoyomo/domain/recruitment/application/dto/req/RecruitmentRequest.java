package com.yoyomo.domain.recruitment.application.dto.req;

import com.yoyomo.domain.recruitment.domain.entity.Schedule;

import java.util.List;

public record RecruitmentRequest(
        String clubId,
        String name,
        Integer generation,
        List<Schedule> calendar
) {
}
