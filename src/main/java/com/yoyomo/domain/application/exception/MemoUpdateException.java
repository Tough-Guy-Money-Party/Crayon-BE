package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class MemoUpdateException extends ApplicationException {
	public MemoUpdateException() {
		super(NOT_FOUND.value(), CANNOT_MEMO_UPDATE.getMessage());
	}
}
