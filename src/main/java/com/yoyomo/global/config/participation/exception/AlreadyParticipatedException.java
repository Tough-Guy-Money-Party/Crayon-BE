package com.yoyomo.global.config.participation.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.global.config.participation.constant.ResponseMessage.ALREADY_PARTICIPATED;
import static org.springframework.http.HttpStatus.CONFLICT;

public class AlreadyParticipatedException extends ApplicationException {
    public AlreadyParticipatedException() {
        super(CONFLICT.value(), ALREADY_PARTICIPATED.getMessage());
    }
}
