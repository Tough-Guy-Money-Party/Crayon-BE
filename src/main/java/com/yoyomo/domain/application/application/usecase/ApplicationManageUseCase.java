package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.req.AssessmentRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationManageUseCase {
    void checkReadPermission(Applicant applicant, Application application);

    void checkDuplicatedApplication(Applicant applicant, String recruitmentId);

    Page<ApplicationResponse> readAll(String recruitmentId, Integer stage, Pageable pageable);

    List<ApplicationResponse> readAllByApplicantName(String recruitmentId, String name, int pageNum);

    ApplicationManageResponse read(String applicationId);

    void update(String id, ApplicationStatusRequest request);

    public void addAssessment(String id, AssessmentRequest request);
}
