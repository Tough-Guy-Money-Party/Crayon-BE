package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.APPLICATION_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ApplicationNotFoundException extends ApplicationException {
    public ApplicationNotFoundException() {
        super(NOT_FOUND.value(), APPLICATION_NOT_FOUND.getMessage());
    }
}
