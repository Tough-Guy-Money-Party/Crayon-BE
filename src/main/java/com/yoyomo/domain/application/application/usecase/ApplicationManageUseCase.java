package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.User;

import java.util.List;

public interface ApplicationManageUseCase {
    void checkReadPermission(User user, Application application);

    void checkDuplicatedApplication(User user, String recruitmentId);

    List<ApplicationResponse> readAll(String recruitmentId);
}
