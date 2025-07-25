package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidDateFormat extends ApplicationException {
	public InvalidDateFormat() {
		super(BAD_REQUEST.value(), INVALID_DATE_FORMAT.getMessage());
	}
}
