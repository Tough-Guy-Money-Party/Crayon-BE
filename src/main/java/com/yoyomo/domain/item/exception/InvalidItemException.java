package com.yoyomo.domain.item.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.INVALID_ITEM;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidItemException extends ApplicationException {
    public InvalidItemException() {
        super(BAD_REQUEST.value(), INVALID_ITEM.getMessage());
    }
}
