package com.yoyomo.domain.recruitment.application.dto.res;

import com.yoyomo.domain.recruitment.domain.entity.Schedule;

import java.util.List;

public record RecruitmentDetailsResponse(
        String name,
        int generation,
        List<Schedule> calendar,
        String formId
) {
}
