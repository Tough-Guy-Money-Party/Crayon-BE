package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAuthService {

    public void checkAuthorization(Application application, User user) {
        if (!application.getUser().equals(user)) {
            throw new AccessDeniedException();
        }
    }
}
