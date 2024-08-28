package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.OVER_DEADLINE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class OutOfDeadlineException extends ApplicationException {
    public OutOfDeadlineException() {
        super(NOT_FOUND.value(), OVER_DEADLINE.getMessage());
    }
}
