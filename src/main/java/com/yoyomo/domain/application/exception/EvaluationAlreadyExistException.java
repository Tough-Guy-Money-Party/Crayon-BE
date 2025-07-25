package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class EvaluationAlreadyExistException extends ApplicationException {
	public EvaluationAlreadyExistException() {
		super(BAD_REQUEST.value(), EVALUATION_ALREADY_EXIST.getMessage());
	}
}
