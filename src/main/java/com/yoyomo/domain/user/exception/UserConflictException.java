package com.yoyomo.domain.user.exception;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class UserConflictException extends ApplicationException {
	public UserConflictException() {
		super(CONFLICT.value(), DUPLICATE_USERNAME.getMessage());
	}
}
