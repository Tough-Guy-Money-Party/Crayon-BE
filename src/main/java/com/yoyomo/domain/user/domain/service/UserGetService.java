package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import com.yoyomo.global.config.exception.ApplicationException;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.jwt.exception.InvalidTokenException;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserGetService {

    @Value("${is-using-refresh-token}")
    private boolean isUsingRefreshToken;

    private final UserRepository userRepository;
    private final KakaoService kakaoService;
    private final JwtProvider jwtProvider;
    public UserResponse login(String token) throws Exception{
        String email = kakaoService.getEmail(token);
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email).get();
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

    public UserResponse testLogin(String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email).get();
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

    public JwtResponse tokenRefresh(String refreshToken, String email) throws Exception {
        if(isUsingRefreshToken){
            JwtResponse jwtResponse = jwtProvider.reissueToken(refreshToken, email);
            return jwtResponse;
        }
        return null;
    }
}
