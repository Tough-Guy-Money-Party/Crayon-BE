package com.yoyomo.domain.form.exception;

import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class FormUnmodifiableException extends ApplicationException {
	public FormUnmodifiableException() {
		super(NOT_ACCEPTABLE.value(), FORM_UNMODIFIABLE.getMessage());
	}
}
