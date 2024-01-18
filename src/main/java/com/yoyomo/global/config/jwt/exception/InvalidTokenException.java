package com.yoyomo.global.config.jwt.exception;

import com.yoyomo.global.config.exception.ApplicationException;
import static com.yoyomo.global.config.jwt.presentation.constant.ResponseMessage.INVALID_TOKEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(UNAUTHORIZED.value(),INVALID_TOKEN.getMessage());
    }
}
