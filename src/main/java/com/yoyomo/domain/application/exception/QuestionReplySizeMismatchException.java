package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class QuestionReplySizeMismatchException extends ApplicationException {
	public QuestionReplySizeMismatchException() {
		super(BAD_REQUEST.value(), QUESTION_REPLY_SIZE_MISMATCH.getMessage());
	}
}
