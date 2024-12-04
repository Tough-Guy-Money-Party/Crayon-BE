package com.yoyomo.domain.mail.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.LAMBDA_INVOKE_FAIL;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class LambdaInvokeException extends ApplicationException {
    public LambdaInvokeException() {
        super(INTERNAL_SERVER_ERROR.value(), LAMBDA_INVOKE_FAIL.getMessage());
    }
}
