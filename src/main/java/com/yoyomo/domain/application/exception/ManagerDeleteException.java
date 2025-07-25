package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ManagerDeleteException extends ApplicationException {
	public ManagerDeleteException() {
		super(BAD_REQUEST.value(), CANNOT_SELF_DELETE.getMessage());
	}
}
