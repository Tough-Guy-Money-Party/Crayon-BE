package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.FAILED_BATCH_INSERT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class BatchInsertFailException extends ApplicationException {
    public BatchInsertFailException() {
        super(INTERNAL_SERVER_ERROR.value(), FAILED_BATCH_INSERT.getMessage());
    }
}
