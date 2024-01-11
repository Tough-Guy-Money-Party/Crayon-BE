package com.yoyomo.global.config.exception;

import com.yoyomo.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(ApplicationException.class)
    public ResponseDto handleApplicationException(ApplicationException ex) {
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), ex.getErrorCode(), ex.getMessage());
        return ResponseDto.of(ex.getErrorCode(), ex.getMessage());
    }
}
