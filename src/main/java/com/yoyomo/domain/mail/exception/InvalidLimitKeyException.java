package com.yoyomo.domain.mail.exception;

import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidLimitKeyException extends ApplicationException {
	public InvalidLimitKeyException(String message) {
		super(BAD_REQUEST.value(), message);
	}
}
