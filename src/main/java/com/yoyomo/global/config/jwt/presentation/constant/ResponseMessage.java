package com.yoyomo.global.config.jwt.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다.");
    private String message;
}
