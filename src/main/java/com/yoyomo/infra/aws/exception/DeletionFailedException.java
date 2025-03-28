package com.yoyomo.infra.aws.exception;

import com.yoyomo.global.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DeletionFailedException extends ApplicationException {
    public DeletionFailedException(Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
