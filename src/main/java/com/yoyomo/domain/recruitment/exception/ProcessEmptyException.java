package com.yoyomo.domain.recruitment.exception;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ProcessEmptyException extends ApplicationException {
	public ProcessEmptyException() {
		super(INTERNAL_SERVER_ERROR.value(), EMPTY_PROCESS.getMessage());
	}
}
