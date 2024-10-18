package com.yoyomo.infra.notion.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.infra.notion.presentation.constant.ResponseMessage.INVALID_LINK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidNotionLinkException extends ApplicationException {
    public InvalidNotionLinkException() {
        super(BAD_REQUEST.value(), INVALID_LINK.getMessage());
    }
}
