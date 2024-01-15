package com.yoyomo.global.config.jwt.exception;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(401,"유효하지 않은 토큰입니다.");
    }
}
