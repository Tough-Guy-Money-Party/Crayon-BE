package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidSortType extends ApplicationException {
	public InvalidSortType() {
		super(BAD_REQUEST.value(), INVALID_DATA_TYPE.getMessage());
	}
}
