package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentModifyRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentDetailsResponse;
import com.yoyomo.domain.recruitment.application.dto.res.RecruitmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RecruitmentManageUseCase {
    void create(RecruitmentRequest request);

    RecruitmentDetailsResponse read(String recruitmentId);

    Page<RecruitmentResponse> readAll(String clubId, PageRequest pageRequest);

    void update(String recruitmentId, RecruitmentModifyRequest request);

    void update(String recruitmentId, FormUpdateRequest request);

    void delete(String recruitmentId);
}
