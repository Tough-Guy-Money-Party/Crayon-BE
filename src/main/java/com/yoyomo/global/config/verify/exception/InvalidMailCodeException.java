package com.yoyomo.global.config.verify.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.global.config.verify.constant.ResponseMessage.INVALID_CODE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidMailCodeException extends ApplicationException {
    public InvalidMailCodeException(){
        super(BAD_REQUEST.value(), INVALID_CODE.getMessage());
    }

}
