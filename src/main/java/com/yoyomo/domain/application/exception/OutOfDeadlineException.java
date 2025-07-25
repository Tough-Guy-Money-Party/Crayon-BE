package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class OutOfDeadlineException extends ApplicationException {
	public OutOfDeadlineException() {
		super(NOT_FOUND.value(), OVER_DEADLINE.getMessage());
	}
}
