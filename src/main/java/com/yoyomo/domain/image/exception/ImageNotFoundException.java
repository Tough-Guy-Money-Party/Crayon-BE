package com.yoyomo.domain.image.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.image.presentation.constant.ResponseMessage.IMAGE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ImageNotFoundException extends ApplicationException {
    public ImageNotFoundException() {
        super(NOT_FOUND.value(), IMAGE_NOT_FOUND.getMessage());
    }
}
