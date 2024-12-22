package com.yoyomo.domain.mail.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.mail.presentation.constant.ResponseMessage.MAIL_NOT_SCHEDULED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MailNotScheduledException extends ApplicationException {
    public MailNotScheduledException() {
        super(NOT_FOUND.value(), MAIL_NOT_SCHEDULED.getMessage());
    }
}
