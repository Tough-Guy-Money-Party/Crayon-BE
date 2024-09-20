package com.yoyomo.domain.user.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.ANONYMOUS_USER;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AnonymousAuthenticationException extends ApplicationException {
    public AnonymousAuthenticationException() {
        super(UNAUTHORIZED.value(), ANONYMOUS_USER.getMessage());
    }
}
