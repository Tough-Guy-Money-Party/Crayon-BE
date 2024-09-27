package com.yoyomo.infra.aws.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.infra.aws.presentation.constant.ResponseMessage.IMAGE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ImageNotFoundException extends ApplicationException {
    public ImageNotFoundException() {
        super(NOT_FOUND.value(), IMAGE_NOT_FOUND.getMessage());
    }
}
