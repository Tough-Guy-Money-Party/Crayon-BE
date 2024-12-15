package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.INTERVIEW_RECORD_ALREADY_EXIST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InterviewRecordAlreadyExistException extends ApplicationException {
    public InterviewRecordAlreadyExistException() {
        super(BAD_REQUEST.value(), INTERVIEW_RECORD_ALREADY_EXIST.getMessage());
    }
}
