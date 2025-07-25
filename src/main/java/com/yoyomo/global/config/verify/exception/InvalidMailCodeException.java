package com.yoyomo.global.config.verify.exception;

import static com.yoyomo.global.config.verify.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidMailCodeException extends ApplicationException {
	public InvalidMailCodeException() {
		super(BAD_REQUEST.value(), INVALID_CODE.getMessage());
	}

}
