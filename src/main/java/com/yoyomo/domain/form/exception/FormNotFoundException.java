package com.yoyomo.domain.form.exception;

import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class FormNotFoundException extends ApplicationException {
	public FormNotFoundException() {
		super(NOT_FOUND.value(), FORM_NOT_FOUND.getMessage());
	}
}
