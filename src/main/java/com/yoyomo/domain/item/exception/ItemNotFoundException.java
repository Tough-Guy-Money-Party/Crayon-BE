package com.yoyomo.domain.item.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.ITEM_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ItemNotFoundException extends ApplicationException {
    public ItemNotFoundException() {
        super(NOT_FOUND.value(), ITEM_NOT_FOUND.getMessage());
    }
}
