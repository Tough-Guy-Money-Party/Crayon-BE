package com.yoyomo.domain.mail.exception;

import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class MailLimitExceededException extends ApplicationException {
	public MailLimitExceededException(String message) {
		super(TOO_MANY_REQUESTS.value(), message);
	}
}
