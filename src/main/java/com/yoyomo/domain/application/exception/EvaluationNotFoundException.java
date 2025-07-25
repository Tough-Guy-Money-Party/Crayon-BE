package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class EvaluationNotFoundException extends ApplicationException {
	public EvaluationNotFoundException() {
		super(NOT_FOUND.value(), EVALUATION_NOT_FOUND.getMessage());
	}
}
