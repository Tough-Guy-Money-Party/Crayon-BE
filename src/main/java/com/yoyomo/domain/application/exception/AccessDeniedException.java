package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class AccessDeniedException extends ApplicationException {
	public AccessDeniedException() {
		super(FORBIDDEN.value(), ACCESS_DENIED.getMessage());
	}
}
