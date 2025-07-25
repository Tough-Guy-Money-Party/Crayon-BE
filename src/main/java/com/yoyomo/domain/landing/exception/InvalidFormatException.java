package com.yoyomo.domain.landing.exception;

import static com.yoyomo.domain.landing.presentation.constant.ResponseMessage.*;

import org.springframework.http.HttpStatus;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidFormatException extends ApplicationException {
	public InvalidFormatException() {
		super(HttpStatus.BAD_REQUEST.value(), INVALID_FORMAT.getMessage());
	}
}
