package com.yoyomo.infra.aws.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.DUPLICATED_SUBDOMAIN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FileNotFoundException extends ApplicationException {
    public FileNotFoundException() {
        super(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
