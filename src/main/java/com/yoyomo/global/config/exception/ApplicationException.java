package com.yoyomo.global.config.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
    private final int errorCode;

    protected ApplicationException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}