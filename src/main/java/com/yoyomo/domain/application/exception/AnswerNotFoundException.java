package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.ANSWER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AnswerNotFoundException extends ApplicationException {
    public AnswerNotFoundException() {
        super(NOT_FOUND.value(), ANSWER_NOT_FOUND.getMessage());
    }
}