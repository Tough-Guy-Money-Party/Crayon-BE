package com.yoyomo.infra.aws.exception;

import static com.yoyomo.infra.aws.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ImageSaveFailureException extends ApplicationException {
	public ImageSaveFailureException() {
		super(INTERNAL_SERVER_ERROR.value(), IMAGE_SAVE_FAILURE.getMessage());
	}
}
