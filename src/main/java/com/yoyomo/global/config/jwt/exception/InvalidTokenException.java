package com.yoyomo.global.config.jwt.exception;

import static com.yoyomo.global.config.jwt.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidTokenException extends ApplicationException {

	public InvalidTokenException() {
		super(UNAUTHORIZED.value(), INVALID_TOKEN.getMessage());
	}

	public InvalidTokenException(Throwable throwable) {
		super(UNAUTHORIZED.value(), INVALID_TOKEN.getMessage());
		log.info("Exception = {}, Message = {}", throwable.getClass(), throwable.getMessage());
	}
}
