package com.yoyomo.domain.mail.exception;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class MailNotScheduledException extends ApplicationException {
	public MailNotScheduledException() {
		super(NOT_FOUND.value(), MAIL_NOT_SCHEDULED.getMessage());
	}
}
