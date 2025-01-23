package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.ALREADY_APPLIED;
import static org.springframework.http.HttpStatus.CONFLICT;

import com.yoyomo.global.config.exception.ApplicationException;

public class AlreadyAppliedException extends ApplicationException {
    public AlreadyAppliedException() {
        super(CONFLICT.value(), ALREADY_APPLIED.getMessage());
    }
}
