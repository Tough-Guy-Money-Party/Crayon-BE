package com.yoyomo.domain.club.exception;

import com.yoyomo.global.config.exception.ApplicationException;

public class UnavailableSubdomainException extends ApplicationException {
    public UnavailableSubdomainException(Integer value, String message) {
        super(value, message);
    }
}
