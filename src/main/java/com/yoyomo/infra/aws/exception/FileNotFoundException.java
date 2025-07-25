package com.yoyomo.infra.aws.exception;

import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class FileNotFoundException extends ApplicationException {
	public FileNotFoundException() {
		super(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
	}
}
