package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;

import java.util.List;

public interface RecruitmentManageUseCase {
    void create(RecruitmentRequest request);

    RecruitmentDetailsResponse read(String recruitmentId);

    List<RecruitmentResponse> readAll(String clubId);

    void update(String recruitmentId, RecruitmentRequest request);

    void update(String recruitmentId, FormUpdateRequest request);

    void delete(String recruitmentId);
}
