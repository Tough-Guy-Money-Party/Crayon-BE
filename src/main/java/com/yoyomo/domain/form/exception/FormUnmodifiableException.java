package com.yoyomo.domain.form.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.form.presentation.constant.ResponseMessage.FORM_UNMODIFIABLE;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class FormUnmodifiableException extends ApplicationException {
    public FormUnmodifiableException() {
        super(NOT_ACCEPTABLE.value(), FORM_UNMODIFIABLE.getMessage());
    }
}
