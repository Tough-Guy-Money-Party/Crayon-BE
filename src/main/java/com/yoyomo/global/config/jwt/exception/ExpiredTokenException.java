package com.yoyomo.global.config.jwt.exception;

import static com.yoyomo.global.config.jwt.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ExpiredTokenException extends ApplicationException {
	public ExpiredTokenException() {
		super(FORBIDDEN.value(), EXPIRED_TOKEN.getMessage());
	}
}
