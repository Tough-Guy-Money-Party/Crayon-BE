package com.yoyomo.domain.club.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.CLUB_NOT_FOUND;
import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.INVALID_NOTION_URL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class InvalidNotionLinkException  extends ApplicationException {
    public InvalidNotionLinkException() {
        super(BAD_REQUEST.value(), INVALID_NOTION_URL.getMessage());
    }
}
