package com.yoyomo.global.config.participation.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.global.config.participation.constant.ResponseMessage.INVALID_CODE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class InvalidPaticipationCodeException extends ApplicationException {
    public InvalidPaticipationCodeException() {
        super(NOT_FOUND.value(), INVALID_CODE.getMessage());
    }
}
