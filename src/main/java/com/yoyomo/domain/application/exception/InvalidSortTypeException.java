package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidSortTypeException extends ApplicationException {
	public InvalidSortTypeException() {
		super(BAD_REQUEST.value(), INVALID_SORT_TYPE.getMessage());
	}
}
