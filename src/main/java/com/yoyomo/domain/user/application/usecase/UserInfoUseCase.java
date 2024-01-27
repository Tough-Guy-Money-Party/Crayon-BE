package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.domain.entity.User;
import org.springframework.security.core.Authentication;

public interface UserInfoUseCase {
    User get(Authentication authentication);
}
