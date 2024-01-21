package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;

import java.util.List;

public interface RecruitmentManageUseCase {
    void create(String formId, RecruitmentRequest request);

    RecruitmentDetailsResponse read(String recruitmentId);

    List<RecruitmentResponse> readAll(String formId);

    void update(String recruitmentId, RecruitmentRequest request);

    void delete(String recruitmentId);
}
