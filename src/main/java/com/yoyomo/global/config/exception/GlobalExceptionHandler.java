package com.yoyomo.global.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yoyomo.global.common.dto.ResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private static final String LOG_FORMAT_WITH_METHOD = "Class = {}, Method = {}, Message = {}, Exception Class = {}";
	private static final String LOG_FORMAT = "Class = {}, Code = {}, Message = {}";

	// 사용자 정의 예외처리
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ResponseDto<Void>> handle(ApplicationException ex) {
		StackTraceElement[] stackTrace = ex.getStackTrace();
		String className = stackTrace[0].getClassName();
		String methodName = stackTrace[0].getMethodName();

		String exceptionMessage = ex.getMessage();

		log.info(LOG_FORMAT_WITH_METHOD, className, methodName, exceptionMessage, ex.getClass().getCanonicalName());

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
		if (ex instanceof ErrorResponse) {
			status = ((ErrorResponse)ex).getStatusCode().value();
		}

		log.error(ex.getMessage(), ex);
		log.error(LOG_FORMAT, ex.getClass().getSimpleName(), status, ex.getMessage());

		return ResponseEntity
			.status(status)
			.body(ResponseDto.of(status, "서버 오류가 발생했습니다"));
	}

	// @Valid에서 발생하는 예외 처리
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDto<Void>> handle(MethodArgumentNotValidException ex) {
		int statusCode = ex.getStatusCode().value();
		String className = ex.getObjectName();
		String error = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

		log.error(LOG_FORMAT, className, statusCode, error);

		return ResponseEntity
			.status(statusCode)
			.body(ResponseDto.of(statusCode, error));
	}
}
