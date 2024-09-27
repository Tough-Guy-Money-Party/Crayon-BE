package com.yoyomo.infra.aws.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.infra.aws.presentation.constant.ResponseMessage.IMAGE_SAVE_FAILURE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ImageSaveFailureException extends ApplicationException {
    public ImageSaveFailureException() {
        super(INTERNAL_SERVER_ERROR.value(), IMAGE_SAVE_FAILURE.getMessage());
    }
}
