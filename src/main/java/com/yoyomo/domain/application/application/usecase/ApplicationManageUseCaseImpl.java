package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.application.exception.AlreadySubmitApplicationException;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCaseImpl implements ApplicationManageUseCase {
    private final ApplicationGetService applicationGetService;
    private final ApplicationUpdateService applicationUpdateService;
    private final ApplicationMapper applicationMapper;

    @Override
    public void checkReadPermission(User user, Application application) {
        if (!user.getId().equals(application.getUser().getId())) {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void checkDuplicatedApplication(User user, String recruitmentId) {
        boolean hasApplication = applicationGetService.exists(user, recruitmentId);
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

    @Override
    public void update(String id, ApplicationStatusRequest request) {
        applicationUpdateService.from(id, request);
    }
}
