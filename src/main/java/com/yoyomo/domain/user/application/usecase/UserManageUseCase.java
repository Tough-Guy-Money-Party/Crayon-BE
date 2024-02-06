package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.user.application.dto.req.RefreshRequest;
import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.domain.user.application.mapper.UserMapper;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserSaveService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import com.yoyomo.domain.user.exception.UserConflictException;
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
    private final UserMapper userMapper;

    private final KakaoService kakaoService;
    private final JwtProvider jwtProvider;

    public UserResponse login(String code) throws Exception {
        String token = kakaoService.getKakaoAccessToken(code);
        String email = kakaoService.getEmail(token);
        if (userGetService.existsByEmail(email)) {
            User user = userGetService.findByEmail(email);
            JwtResponse tokenDto = new JwtResponse(
                    jwtProvider.createAccessToken(user.getEmail()),
                    jwtProvider.createRefreshToken(user.getEmail())
            );
            UserResponse signResponse = UserResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .token(tokenDto)
                    .build();
            return signResponse;
        } else {
            return this.register(email);
        }
    }

    public UserResponse register(String email) {
        if (userGetService.existsByEmail(email)) {
            throw new UserConflictException();
        }
        User user = User.builder()
                .email(email)
                .build();
        userSaveService.save(user);
        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(user.getEmail()),
                jwtProvider.createRefreshToken(user.getEmail())
        );
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(tokenDto)
                .build();
        return signResponse;
    }

    public JwtResponse tokenRefresh(RefreshRequest request) {
        if (isUsingRefreshToken) {
            JwtResponse jwtResponse = jwtProvider.reissueToken(request.getRefreshToken(), request.getEmail());
            return jwtResponse;
        }
        return null;
    }

    public User create(ApplicationRequest request) {
        User user = userMapper.from(request);
        return userSaveService.save(user);
    }

    public Void update(Authentication authentication) {
        return userUpdateService.updateByEmail(authentication.getName());
    }

    public Void delete(Authentication authentication) {
        return userUpdateService.deleteByEmail(authentication.getName());
    }
}
