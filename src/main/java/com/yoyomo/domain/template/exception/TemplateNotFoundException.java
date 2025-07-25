package com.yoyomo.domain.template.exception;

import static com.yoyomo.domain.template.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class TemplateNotFoundException extends ApplicationException {
	public TemplateNotFoundException() {
		super(NOT_FOUND.value(), TEMPLATE_NOT_FOUND.getMessage());
	}
}
