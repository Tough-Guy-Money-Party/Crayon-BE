package com.yoyomo.domain.item.exception;

import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidItemException extends ApplicationException {
	public InvalidItemException() {
		super(BAD_REQUEST.value(), INVALID_ITEM.getMessage());
	}
}
