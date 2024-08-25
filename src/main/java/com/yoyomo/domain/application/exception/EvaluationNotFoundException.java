package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.EVALUATION_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class EvaluationNotFoundException extends ApplicationException {
    public EvaluationNotFoundException() {
        super(NOT_FOUND.value(), EVALUATION_NOT_FOUND.getMessage());
    }
}