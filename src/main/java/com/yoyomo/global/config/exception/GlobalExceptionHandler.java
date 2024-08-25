package com.yoyomo.global.config.exception;

import com.yoyomo.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(ApplicationException.class)
    public ResponseDto<Void> handle(ApplicationException ex) {
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), ex.getErrorCode(), ex.getMessage());
        return ResponseDto.of(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseDto<Void> handle(BindException ex) {
        int status = 400;
        String message = ex.getMessage();

        if (ex instanceof ErrorResponse) {
            status = ((ErrorResponse) ex).getStatusCode().value();
            message = ex.getBindingResult().getFieldErrors().get(0).getField() + " " + ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, message);

        return ResponseDto.of(status, message);
    }
}
