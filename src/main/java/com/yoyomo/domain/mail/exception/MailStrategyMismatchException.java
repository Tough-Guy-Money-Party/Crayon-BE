package com.yoyomo.domain.mail.exception;

import com.yoyomo.global.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.MAIL_STRATEGY_MISMATCH;

public class MailStrategyMismatchException extends ApplicationException {
    public MailStrategyMismatchException() {
        super(HttpStatus.BAD_REQUEST.value(), MAIL_STRATEGY_MISMATCH.getMessage());
    }
}
