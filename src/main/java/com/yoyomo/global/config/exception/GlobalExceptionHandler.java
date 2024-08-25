package com.yoyomo.global.config.exception;

import com.yoyomo.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


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
    public ResponseDto<List<ExceptionDTO>> handle(BindException ex) {
        int status = 400;
        List<ExceptionDTO> dto = new ArrayList<>();

        if (ex instanceof ErrorResponse) {
            status = ((ErrorResponse) ex).getStatusCode().value();
            ex.getBindingResult().getFieldErrors()
                    .forEach(error -> dto.add(new ExceptionDTO(error.getField(), error.getDefaultMessage(), error.getRejectedValue())));
        } else {
            dto.add(new ExceptionDTO(null, ex.getMessage(), null));
        }
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, dto);

        return ResponseDto.of(status, ex.getMessage(), dto);
    }
}
