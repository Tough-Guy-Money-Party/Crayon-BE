package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.EVALUATION_ALREADY_EXIST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class EvaluationAlreadyExistException extends ApplicationException {
    public EvaluationAlreadyExistException() {
        super(BAD_REQUEST.value(), EVALUATION_ALREADY_EXIST.getMessage());
    }
}
