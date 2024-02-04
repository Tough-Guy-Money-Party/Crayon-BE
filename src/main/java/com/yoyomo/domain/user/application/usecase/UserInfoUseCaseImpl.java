package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoUseCaseImpl implements UserInfoUseCase {
    private final UserGetService userGetService;

    public User get(Authentication authentication) {
        return userGetService.findByEmail(authentication.getName());
    }

    public User get(String name, String phone) {
        return userGetService.find(name, phone).orElseThrow(UserNotFoundException::new);
    }
}
