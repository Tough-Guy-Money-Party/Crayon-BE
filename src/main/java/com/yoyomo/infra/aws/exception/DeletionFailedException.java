package com.yoyomo.infra.aws.exception;

import org.springframework.http.HttpStatus;

import com.yoyomo.global.config.exception.ApplicationException;

public class DeletionFailedException extends ApplicationException {
	public DeletionFailedException(Exception exception) {
		super(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
	}
}
