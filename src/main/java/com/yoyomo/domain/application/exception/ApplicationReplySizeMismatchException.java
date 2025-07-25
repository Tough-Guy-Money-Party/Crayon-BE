package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ApplicationReplySizeMismatchException extends ApplicationException {
	public ApplicationReplySizeMismatchException() {
		super(BAD_REQUEST.value(), APPLICATION_REPLY_SIZE_MISMATCH.getMessage());
	}
}
