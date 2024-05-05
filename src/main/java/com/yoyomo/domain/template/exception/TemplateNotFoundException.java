package com.yoyomo.domain.template.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.template.presentation.constant.ResponseMessage.TEMPLATE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class TemplateNotFoundException extends ApplicationException {
    public TemplateNotFoundException() {
        super(NOT_FOUND.value(), TEMPLATE_NOT_FOUND.getMessage());
    }
}
