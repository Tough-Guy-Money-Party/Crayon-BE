package com.yoyomo.domain.application.application.dto.req;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;

import java.util.List;

public record ApplicationRequest(
        String recruitmentId,
        List<Answer> answers,
        SubmitStatus submitStatus
) {
}
