package com.yoyomo.domain.user.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.DUPLICATE_USERNAME;
import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.NEED_REGISTER;
import static org.springframework.http.HttpStatus.CREATED;

public class NeedRegisterException extends ApplicationException {
    public NeedRegisterException(String email) {
        super(CREATED.value(), NEED_REGISTER.getMessage()+email);
    }
}
