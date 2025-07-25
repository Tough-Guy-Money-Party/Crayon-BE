package com.yoyomo.infra.aws.exception;

import static com.yoyomo.infra.aws.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ImageNotFoundException extends ApplicationException {
	public ImageNotFoundException() {
		super(NOT_FOUND.value(), IMAGE_NOT_FOUND.getMessage());
	}
}
