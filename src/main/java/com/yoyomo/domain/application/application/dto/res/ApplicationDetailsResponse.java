package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;

import java.util.List;

public record ApplicationDetailsResponse(
        List<Answer> answers,
        SubmitStatus submitStatus
) {
}
