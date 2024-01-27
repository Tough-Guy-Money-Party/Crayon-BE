package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.ALREADY_SUBMIT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AlreadySubmitApplicationException extends ApplicationException {
    public AlreadySubmitApplicationException() {
        super(BAD_REQUEST.value(), ALREADY_SUBMIT.getMessage());
    }
}
