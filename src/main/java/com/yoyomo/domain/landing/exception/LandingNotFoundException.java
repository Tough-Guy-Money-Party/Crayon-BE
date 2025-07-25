package com.yoyomo.domain.landing.exception;

import static com.yoyomo.domain.landing.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class LandingNotFoundException extends ApplicationException {
	public LandingNotFoundException() {
		super(NOT_FOUND.value(), LANDING_NOT_FOUND.getMessage());
	}
}
