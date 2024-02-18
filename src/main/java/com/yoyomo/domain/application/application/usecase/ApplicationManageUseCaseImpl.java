package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.ApplicationStatus;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.exception.AlreadySubmitApplicationException;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCaseImpl implements ApplicationManageUseCase {
    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;

    @Override
    public void checkReadPermission(Applicant applicant, Application application) {
        if (!applicant.getId().equals(application.getApplicant().getId())) {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void checkDuplicatedApplication(Applicant applicant, String recruitmentId) {
        boolean hasApplication = applicationGetService.exists(applicant, recruitmentId);
        if (hasApplication) {
            throw new AlreadySubmitApplicationException();
        }
    }

    @Override
    public List<ApplicationResponse> readAll(String recruitmentId) {
        List<Application> applications = applicationGetService.findAll(recruitmentId);
        return applications.stream()
                .map(applicationMapper::mapToApplicationResponse)
                .toList();
    }

    @Override
    public ApplicationManageResponse read(String applicationId) {
        Application application = applicationGetService.find(applicationId);
        return applicationMapper.mapToApplicationManage(application);
    }
}
