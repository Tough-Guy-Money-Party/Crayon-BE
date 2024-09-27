package com.yoyomo.global.config.exception;

import com.yoyomo.global.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    // 사용자 정의 예외처리
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseDto<Void>> handle(ApplicationException ex) {
        // 개발 용이성을 위해 전체 에러 로그를 다시 출력. 개발 완료 후 제거할 것
        log.error(ex.getMessage(), ex);
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), ex.getErrorCode(), ex.getMessage());

        return ResponseEntity
                .status(ex.getErrorCode())
                .body(ResponseDto.of(ex.getErrorCode(), ex.getMessage()));
    }

    // HTTP 요청이 잘못된 경우
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ResponseDto<Void>> handle(ServletRequestBindingException ex) {
        int status = ex.getStatusCode().value();

        log.error(ex.getMessage(), ex);
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(status, ex.getMessage()));
    }

    // 파라미터가 없는 경우 예외처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<Void>> handle(HttpMessageNotReadableException ex) {
        int status = 400;

        log.error(ex.getMessage(), ex);
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(status, ex.getMessage()));
    }

    // 입력값이 잘못 된 경우 예외처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Void>> handle(IllegalArgumentException ex) {
        int status = 400;

        log.error(ex.getMessage(), ex);
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(status, ex.getMessage()));
    }

    // 위 예외를 제외한 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handle(Exception ex) {
        int status = 500;

        // 발생한 예외가 ErrorResponse에 속한다면 예외에서 상태 코드 추출
        if(ex instanceof ErrorResponse){
            status = ((ErrorResponse)ex).getStatusCode().value();
        }

        log.error(ex.getMessage(), ex);
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(status, ex.getMessage()));
    }

    // @Valid에서 발생하는 예외 처리
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseDto<List<ExceptionDTO>>> handle(BindException ex, HttpServletRequest request) {
        int status = 400;
        List<ExceptionDTO> dto = new ArrayList<>();

        String url = request.getMethod() + " " + request.getRequestURL().toString();

        if (ex instanceof ErrorResponse) {
            status = ((ErrorResponse) ex).getStatusCode().value();
            ex.getBindingResult().getFieldErrors()
                    .forEach(error -> dto.add(new ExceptionDTO(url, error.getField(), error.getDefaultMessage(), error.getRejectedValue())));
        } else {
            dto.add(new ExceptionDTO(url, null, ex.getMessage(), null));
        }

        log.error(ex.getMessage(), ex);
        log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, dto);

        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(status, ex.getMessage(), dto));
    }

}
