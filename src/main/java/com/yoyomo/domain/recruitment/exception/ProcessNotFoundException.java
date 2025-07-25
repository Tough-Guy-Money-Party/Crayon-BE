package com.yoyomo.domain.recruitment.exception;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ProcessNotFoundException extends ApplicationException {
	public ProcessNotFoundException() {
		super(NOT_FOUND.value(), PROCESS_NOT_FOUND.getMessage());
	}
}
