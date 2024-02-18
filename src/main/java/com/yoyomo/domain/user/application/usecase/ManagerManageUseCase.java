package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.application.dto.req.RefreshRequest;
import com.yoyomo.domain.user.application.dto.req.RegisterRequest;
import com.yoyomo.domain.user.application.dto.res.ManagerResponse;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.domain.service.UserSaveService;
import com.yoyomo.domain.user.domain.service.UserUpdateService;
import com.yoyomo.domain.user.exception.UserConflictException;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.KakaoService;
import com.yoyomo.global.config.kakao.dto.KakaoInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerManageUseCase {

    @Value("${is-using-refresh-token}")
    private boolean isUsingRefreshToken;

    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final UserUpdateService userUpdateService;

    private final KakaoService kakaoService;
    private final JwtProvider jwtProvider;

    private final RedisTemplate<String, String> redisTemplate;
    public static final long EXPIRATION_TIME = 60 * 60 * 1000L;

    public static final String EMAIL_INFO_KEY_PREFIX = "email";
    public static final String NAME_INFO_KEY_PREFIX = "name";

    public ManagerResponse login(String code) throws Exception {
        String token = kakaoService.getKakaoAccessToken(code);
        KakaoInfoResponse kakaoInfoResponse = kakaoService.getInfo(token);
        String email = kakaoInfoResponse.getEmail();
        if (userGetService.existsByEmail(email)) {
            Manager manager = userGetService.findByEmail(email);
            JwtResponse tokenDto = new JwtResponse(
                    jwtProvider.createAccessToken(manager.getEmail()),
                    jwtProvider.createRefreshToken(manager.getEmail())
            );
            ManagerResponse signResponse = ManagerResponse.builder()
                    .id(manager.getId())
                    .email(manager.getEmail())
                    .name(manager.getName())
                    .token(tokenDto)
                    .build();
            return signResponse;
        } else {
            String name = kakaoInfoResponse.getName();
            redisTemplate.opsForValue().set(
                    EMAIL_INFO_KEY_PREFIX+code,
                    email,
                    EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );
            redisTemplate.opsForValue().set(
                    NAME_INFO_KEY_PREFIX+code,
                    name,
                    EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );
            throw new UserNotFoundException();
        }
    }

    public ManagerResponse register(RegisterRequest request) {
        String email = redisTemplate.opsForValue().get(EMAIL_INFO_KEY_PREFIX+request.getCode());
        String name = redisTemplate.opsForValue().get(NAME_INFO_KEY_PREFIX+request.getCode());
        if (userGetService.existsByEmail(email)) {
            throw new UserConflictException();
        }
        Manager manager = Manager.builder()
                .email(email)
                .name(name)
                .build();
        userSaveService.save(manager);
        JwtResponse tokenDto = new JwtResponse(
                jwtProvider.createAccessToken(manager.getEmail()),
                jwtProvider.createRefreshToken(manager.getEmail())
        );
        ManagerResponse signResponse = ManagerResponse.builder()
                .id(manager.getId())
                .name(manager.getName())
                .email(manager.getEmail())
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

    public Void update(Authentication authentication) {
        return userUpdateService.updateByEmail(authentication.getName());
    }

    public Void delete(Authentication authentication) {
        return userUpdateService.deleteByEmail(authentication.getName());
    }
}
