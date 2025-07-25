package com.yoyomo.domain.mail.exception;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class MailAlreadySentException extends ApplicationException {
	public MailAlreadySentException() {
		super(BAD_REQUEST.value(), MAIL_ALREADY_SENT.getMessage());
	}
}
