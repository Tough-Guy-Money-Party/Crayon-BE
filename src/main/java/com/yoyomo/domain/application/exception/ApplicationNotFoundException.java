package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ApplicationNotFoundException extends ApplicationException {
	public ApplicationNotFoundException() {
		super(NOT_FOUND.value(), APPLICATION_NOT_FOUND.getMessage());
	}
}
