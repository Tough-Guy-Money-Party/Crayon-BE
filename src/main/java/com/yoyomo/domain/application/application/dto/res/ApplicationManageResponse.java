package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;
import com.yoyomo.domain.interview.application.dto.InterviewResponse;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;

import java.util.List;

public record ApplicationManageResponse(
        List<Answer> answers,
        SubmitStatus submitStatus,
        List<ItemResponse> items,
        InterviewResponse interview
) {}
