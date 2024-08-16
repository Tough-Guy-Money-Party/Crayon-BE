package com.yoyomo.domain.club.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.DUPLICATED_SUBDOMAIN;
import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicatedSubDomainException extends ApplicationException {
    public DuplicatedSubDomainException() {
        super(CONFLICT.value(), DUPLICATED_SUBDOMAIN.getMessage());
    }
}
