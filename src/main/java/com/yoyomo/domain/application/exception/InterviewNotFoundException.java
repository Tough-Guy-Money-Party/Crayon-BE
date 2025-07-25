package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InterviewNotFoundException extends ApplicationException {
	public InterviewNotFoundException() {
		super(NOT_FOUND.value(), INTERVIEW_RECORD_NOT_FOUND.getMessage());
	}
}
