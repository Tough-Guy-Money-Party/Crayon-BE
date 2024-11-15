package com.yoyomo.domain.mail.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CreateScheduleException extends ApplicationException {
    public CreateScheduleException(String message) {
        super(BAD_REQUEST.value(), message);
    }
}
