package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidFilterTypeException extends ApplicationException {
	public InvalidFilterTypeException() {
		super(BAD_REQUEST.value(), INVALID_FILTER_TYPE.getMessage());
	}
}
