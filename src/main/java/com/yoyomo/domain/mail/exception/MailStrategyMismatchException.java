package com.yoyomo.domain.mail.exception;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.*;

import org.springframework.http.HttpStatus;

import com.yoyomo.global.config.exception.ApplicationException;

public class MailStrategyMismatchException extends ApplicationException {
	public MailStrategyMismatchException() {
		super(HttpStatus.BAD_REQUEST.value(), MAIL_STRATEGY_MISMATCH.getMessage());
	}
}
