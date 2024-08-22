package com.yoyomo.domain.recruitment.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.PROCESS_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ProcessNotFoundException extends ApplicationException {
    public ProcessNotFoundException() {
        super(NOT_FOUND.value(), PROCESS_NOT_FOUND.getMessage());
    }
}