package com.yoyomo.domain.user.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.ACCESS_DENIED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AccessDeniedException extends ApplicationException {
    public AccessDeniedException() {
        super(FORBIDDEN.value(), ACCESS_DENIED.getMessage());
    }
}
