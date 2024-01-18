package com.yoyomo.domain.user.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.DUPLICATE_USERNAME;
import static org.springframework.http.HttpStatus.CONFLICT;

public class UserConflictException  extends ApplicationException {
    public UserConflictException() {
        super(CONFLICT.value(), DUPLICATE_USERNAME.getMessage());
    }
}
