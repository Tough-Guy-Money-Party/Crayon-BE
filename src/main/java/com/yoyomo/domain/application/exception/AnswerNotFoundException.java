package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class AnswerNotFoundException extends ApplicationException {
	public AnswerNotFoundException() {
		super(NOT_FOUND.value(), ANSWER_NOT_FOUND.getMessage());
	}
}
