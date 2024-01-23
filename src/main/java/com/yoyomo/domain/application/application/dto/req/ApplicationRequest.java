package com.yoyomo.domain.application.application.dto.req;

import com.yoyomo.domain.application.domain.entity.SubmitStatus;

import java.util.Map;

public record ApplicationRequest(
        String recruitmentId,
        Map<String, String> answers,
        SubmitStatus submitStatus
) {
}
