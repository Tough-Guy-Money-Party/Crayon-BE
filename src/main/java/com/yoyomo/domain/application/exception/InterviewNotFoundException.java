package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.INTERVIEW_RECORD_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class InterviewNotFoundException extends ApplicationException {
    public InterviewNotFoundException() {
        super(NOT_FOUND.value(), INTERVIEW_RECORD_NOT_FOUND.getMessage());
    }
}
