package com.yoyomo.domain.mail.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidLimitKeyException extends ApplicationException {
    public InvalidLimitKeyException(String message) {
        super(BAD_REQUEST.value(), message);
    }
}
