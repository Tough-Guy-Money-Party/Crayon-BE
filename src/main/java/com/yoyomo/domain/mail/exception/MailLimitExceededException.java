package com.yoyomo.domain.mail.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

public class MailLimitExceededException extends ApplicationException {
    public MailLimitExceededException(String message) {
        super(TOO_MANY_REQUESTS.value(), message);
    }
}
