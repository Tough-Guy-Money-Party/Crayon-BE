package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;
import com.yoyomo.domain.interview.application.dto.InterviewResponse;

import java.util.List;

public record ApplicationManageResponse(
        SubmitStatus submitStatus,
        List<Answer> answers,
        InterviewResponse interview
) {}
