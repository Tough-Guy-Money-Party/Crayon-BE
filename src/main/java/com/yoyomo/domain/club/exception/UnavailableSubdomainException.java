package com.yoyomo.domain.club.exception;

import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class UnavailableSubdomainException extends ApplicationException {
	public UnavailableSubdomainException(Integer value, String message) {
		super(value, message);
	}

	public UnavailableSubdomainException(String message) {
		super(INTERNAL_SERVER_ERROR.value(), message);
	}
}
