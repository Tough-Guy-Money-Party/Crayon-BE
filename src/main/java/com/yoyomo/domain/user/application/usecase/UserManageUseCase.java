package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.user.application.dto.req.*;
import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserSaveService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserManageUseCase {
    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final UserUpdateService userUpdateService;

    public UserResponse login(LoginRequest request) throws Exception {
        return userGetService.login(request.getAccessToken());
    }

    public Void register(RegisterRequest request) throws Exception {
        return userSaveService.register(request.getAccessToken(), request.getName(), request.getNumber());
    }

    public UserResponse testLogin(TestLoginRequest request) throws Exception {
        return userGetService.testLogin(request.getEmail());
    }

    public Void testRegister(TestRegisterRequest request) throws Exception {
        return userSaveService.testRegister(request.getEmail(), request.getName(), request.getNumber());
    }

    public JwtResponse tokenRefresh(RefreshRequest request) throws Exception {
        return userGetService.tokenRefresh(request.getRefreshToken(), request.getEmail());
    }

    public Void update(Authentication authentication) {
        return userUpdateService.update(authentication.getName());
    }

    public Void delete(Authentication authentication) {
        return userUpdateService.delete(authentication.getName());
    }
}
