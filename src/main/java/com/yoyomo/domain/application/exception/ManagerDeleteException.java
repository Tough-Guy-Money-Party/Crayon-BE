package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.CANNOT_SELF_DELETE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ManagerDeleteException extends ApplicationException {
    public ManagerDeleteException() {
        super(BAD_REQUEST.value(), CANNOT_SELF_DELETE.getMessage());
    }
}
