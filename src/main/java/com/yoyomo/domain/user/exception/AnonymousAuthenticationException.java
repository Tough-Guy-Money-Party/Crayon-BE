package com.yoyomo.domain.user.exception;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class AnonymousAuthenticationException extends ApplicationException {
	public AnonymousAuthenticationException() {
		super(UNAUTHORIZED.value(), ANONYMOUS_USER.getMessage());
	}
}
