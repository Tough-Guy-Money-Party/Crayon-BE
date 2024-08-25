package com.yoyomo.global.config.exception;

public record ExceptionDTO(
        String source,
        String message,
        Object rejectedValue
) {}
