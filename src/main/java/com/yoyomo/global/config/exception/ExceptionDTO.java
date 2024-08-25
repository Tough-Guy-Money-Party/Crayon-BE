package com.yoyomo.global.config.exception;

public record ExceptionDTO(
        String requestURL,
        String source,
        String message,
        Object rejectedValue
) {}
