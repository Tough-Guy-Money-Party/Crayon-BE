package com.yoyomo.domain.recruitment.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.EMPTY_PROCESS;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ProcessEmptyException extends ApplicationException {
    public ProcessEmptyException() {
        super(INTERNAL_SERVER_ERROR.value(), EMPTY_PROCESS.getMessage());
    }
}