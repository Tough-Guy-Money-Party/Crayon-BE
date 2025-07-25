package com.yoyomo.domain.form.exception;

import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class FormCanNotRemoveException extends ApplicationException {
	public FormCanNotRemoveException() {
		super(NOT_ACCEPTABLE.value(), FORM_CAN_NOT_REMOVE.getMessage());
	}
}
