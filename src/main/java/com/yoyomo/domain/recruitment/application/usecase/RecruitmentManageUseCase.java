package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;

public interface RecruitmentManageUseCase {
    void create(String formId, RecruitmentRequest request);

}
