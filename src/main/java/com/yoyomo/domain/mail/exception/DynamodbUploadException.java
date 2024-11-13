package com.yoyomo.domain.mail.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.MAIL_DB_UPLOAD_FAIL;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class DynamodbUploadException extends ApplicationException {
    public DynamodbUploadException() {
        super(INTERNAL_SERVER_ERROR.value(), MAIL_DB_UPLOAD_FAIL.getMessage());
    }
}
