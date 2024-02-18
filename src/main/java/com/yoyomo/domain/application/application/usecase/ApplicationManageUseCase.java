package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;

import java.util.List;

public interface ApplicationManageUseCase {
    void checkReadPermission(Applicant applicant, Application application);

    void checkDuplicatedApplication(Applicant applicant, String recruitmentId);

    List<ApplicationResponse> readAll(String recruitmentId);

    ApplicationManageResponse read(String applicationId);
}
