package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationSaveService;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyUseCaseImpl implements ApplyUseCase {
    private final ApplicationManageUseCase applicationManageUseCase;
    private final ApplicationSaveService applicationSaveService;
    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;

    public void create(User user, ApplicationRequest applicationRequest) {
        Application application = applicationMapper.from(user, applicationRequest);
        applicationSaveService.save(application);
    }

    @Override
    public ApplicationDetailsResponse read(User user, String applicationId) {
        Application application = applicationGetService.find(applicationId);
        applicationManageUseCase.checkReadPermission(user, application);
        return applicationMapper.mapToApplicationDetails(application);
    }
}
