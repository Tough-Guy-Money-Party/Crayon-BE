package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class AlreadyAppliedException extends ApplicationException {
	public AlreadyAppliedException() {
		super(CONFLICT.value(), ALREADY_APPLIED.getMessage());
	}
}
