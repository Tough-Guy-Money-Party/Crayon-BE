package com.yoyomo.domain.mail.exception;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class DynamodbUploadException extends ApplicationException {
	public DynamodbUploadException() {
		super(INTERNAL_SERVER_ERROR.value(), MAIL_DB_UPLOAD_FAIL.getMessage());
	}
}
