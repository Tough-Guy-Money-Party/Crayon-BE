package com.yoyomo.domain.image.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.image.presentation.constant.ResponseMessage.IMAGE_SAVE_FAILURE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ImageSaveFailureException extends ApplicationException {
    public ImageSaveFailureException() {
        super(INTERNAL_SERVER_ERROR.value(), IMAGE_SAVE_FAILURE.getMessage());
    }
}
