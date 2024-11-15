package com.yoyomo.domain.template.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class SesTemplateException extends ApplicationException {
    public SesTemplateException(String message) {
        super(INTERNAL_SERVER_ERROR.value(), message);
    }
}
