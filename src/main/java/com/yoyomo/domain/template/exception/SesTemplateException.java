package com.yoyomo.domain.template.exception;

import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class SesTemplateException extends ApplicationException {
	public SesTemplateException(String message) {
		super(INTERNAL_SERVER_ERROR.value(), message);
	}
}
