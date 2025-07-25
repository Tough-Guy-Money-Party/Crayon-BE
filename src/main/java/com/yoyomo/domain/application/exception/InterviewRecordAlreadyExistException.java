package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InterviewRecordAlreadyExistException extends ApplicationException {
	public InterviewRecordAlreadyExistException() {
		super(BAD_REQUEST.value(), INTERVIEW_RECORD_ALREADY_EXIST.getMessage());
	}
}
