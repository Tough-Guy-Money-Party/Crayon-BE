package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.User;

public interface ApplicationManageUseCase {
    void checkReadPermission(User user, Application application);
}
