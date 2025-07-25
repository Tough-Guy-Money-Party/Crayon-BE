package com.yoyomo.domain.user.exception;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {
	public UserNotFoundException() {
		super(NOT_FOUND.value(), USER_NOT_FOUND.getMessage());
	}
}
