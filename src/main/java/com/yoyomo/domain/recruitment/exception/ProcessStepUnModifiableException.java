package com.yoyomo.domain.recruitment.exception;

import static org.springframework.http.HttpStatus.*;

import com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage;
import com.yoyomo.global.config.exception.ApplicationException;

public class ProcessStepUnModifiableException extends ApplicationException {
	public ProcessStepUnModifiableException(ResponseMessage message) {
		super(BAD_REQUEST.value(), message.getMessage());
	}
}
