package com.yoyomo.global.config.jwt.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtError {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,401, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "만료된 토큰입니다."),
    TOKEN_NOT_EXIST(HttpStatus.NOT_FOUND, 404, "토큰이 존재하지 않습니다.");

    private final HttpStatus status;
    private final int code;
    private final String message;
}
