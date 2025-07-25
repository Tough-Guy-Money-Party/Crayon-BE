package com.yoyomo.domain.mail.exception;

import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class LambdaInvokeException extends ApplicationException {
	public LambdaInvokeException(String message) {
		super(INTERNAL_SERVER_ERROR.value(), message);
	}
}
