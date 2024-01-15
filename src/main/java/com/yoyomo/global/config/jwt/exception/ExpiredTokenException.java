package com.yoyomo.global.config.jwt.exception;

import com.yoyomo.global.config.exception.ApplicationException;

public class ExpiredTokenException extends ApplicationException {
    public ExpiredTokenException() {
        super(403, "만료된 토큰입니다.");
    }
}