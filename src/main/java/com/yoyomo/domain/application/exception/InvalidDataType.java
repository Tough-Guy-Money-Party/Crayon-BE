package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.INVALID_DATA_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidDataType extends ApplicationException {
    public InvalidDataType() {
        super(BAD_REQUEST.value(), INVALID_DATA_TYPE.getMessage());
    }
}
