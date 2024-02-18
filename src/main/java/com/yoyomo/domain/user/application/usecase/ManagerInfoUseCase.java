package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerInfoUseCase implements UserInfoUseCase{
    private final UserGetService userGetService;

    @Override
    public Manager get(Authentication authentication) {
        return userGetService.findByEmail(authentication.getName());
    }
}
