package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.exception.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationManageUseCaseImpl implements ApplicationManageUseCase {
    @Override
    public void checkReadPermission(User user, Application application) {
        if (!user.getId().equals(application.getUserId())) {
            throw new AccessDeniedException();
        }
    }
}
