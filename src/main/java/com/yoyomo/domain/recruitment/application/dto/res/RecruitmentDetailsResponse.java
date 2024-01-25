package com.yoyomo.domain.recruitment.application.dto.res;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.recruitment.domain.entity.Schedule;

import java.util.List;

public record RecruitmentDetailsResponse(
        String title,
        int generation,
        List<Schedule> calendar,
        List<String> process,
        Form form
) {
}
