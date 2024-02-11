package com.yoyomo.domain.interview.application.usecase;

import com.yoyomo.domain.interview.application.dto.InterviewRequest;

public interface InterviewManageUseCase {
    void create(String applicationId, InterviewRequest request);
}
