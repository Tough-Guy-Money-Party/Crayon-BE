package com.yoyomo.domain.club.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.DEPLICATED_SUBDOMAIN;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.UNUSABLE_SUBDOMAIN;
import static org.springframework.http.HttpStatus.CONFLICT;

public class UnavailableSubdomainException extends ApplicationException {
    public UnavailableSubdomainException(Integer value, String message) {
        super(value, message);
    }
}
