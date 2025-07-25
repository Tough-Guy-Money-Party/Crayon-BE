package com.yoyomo.infra.notion.exception;

import static com.yoyomo.infra.notion.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class InvalidNotionLinkException extends ApplicationException {
	public InvalidNotionLinkException() {
		super(BAD_REQUEST.value(), INVALID_LINK.getMessage());
	}
}
