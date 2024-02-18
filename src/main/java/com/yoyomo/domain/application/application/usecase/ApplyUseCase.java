package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;

import java.util.List;

public interface ApplyUseCase {
    void create(ApplicationRequest applicationRequest);

    void update(Applicant applicant, String applicationId, ApplicationRequest applicationRequest);

    ApplicationDetailsResponse read(Applicant applicant, String applicationId);

    List<MyApplicationsResponse> readAll(Applicant applicant);
}
