package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.application.dto.req.*;
import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserSaveService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import com.yoyomo.domain.user.exception.UserConflictException;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserManageUseCase {

    @Value("${is-using-refresh-token}")
    private boolean isUsingRefreshToken;

    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final UserUpdateService userUpdateService;

    private final KakaoService kakaoService;
    private final JwtProvider jwtProvider;

    public UserResponse login(LoginRequest request) throws Exception {
        String email = kakaoService.getEmail(request.getAccessToken());
        if (userGetService.existsByEmail(email)) {
            User user = userGetService.findByEmail(email);
            JwtResponse tokenDto = new JwtResponse(
                    jwtProvider.createAccessToken(user.getEmail()),
                    jwtProvider.createRefreshToken(user.getEmail())
            );
            UserResponse signResponse = UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .number(user.getNumber())
                    .token(tokenDto)
                    .build();
            return signResponse;
        }
        throw new UserNotFoundException();
    }

    public Void register(RegisterRequest request) throws Exception {
        String email = kakaoService.getEmail(request.getAccessToken());
        if (userGetService.existsByEmail(email)){
            throw new UserConflictException();
        }
        User user = User.builder()
                .email(email)
                .name(request.getName())
                .number(request.getNumber())
                .build();
        userSaveService.save(user);
        return null;
    }

    public UserResponse testLogin(TestLoginRequest request) {
        if (userGetService.existsByEmail(request.getEmail())) {
            User user = userGetService.findByEmail(request.getEmail());
            JwtResponse tokenDto = new JwtResponse(
                    jwtProvider.createAccessToken(user.getEmail()),
                    isUsingRefreshToken
                            ? jwtProvider.createRefreshToken(user.getEmail())
                            : "No Refresh Token Provided"
            );
            UserResponse signResponse = UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .number(user.getNumber())
                    .token(tokenDto)
                    .build();
            return signResponse;
        }
        throw new UserNotFoundException();
    }

    public Void testRegister(TestRegisterRequest request) throws Exception {
        if (userGetService.existsByEmail(request.getEmail())){
            throw new UserConflictException();
        }
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .number(request.getNumber())
                .build();
        userSaveService.save(user);
        return null;
    }

    public JwtResponse tokenRefresh(RefreshRequest request) throws Exception {
        if(isUsingRefreshToken){
            JwtResponse jwtResponse = jwtProvider.reissueToken(request.getRefreshToken(), request.getEmail());
            return jwtResponse;
        }
        return null;
    }

    public Void update(Authentication authentication) {
        return userUpdateService.updateByEmail(authentication.getName());
    }

    public Void delete(Authentication authentication) {
        return userUpdateService.deleteByEmail(authentication.getName());
    }
}
