package com.yoyomo.domain.application.exception;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class BatchInsertFailException extends ApplicationException {
	public BatchInsertFailException() {
		super(INTERNAL_SERVER_ERROR.value(), FAILED_BATCH_INSERT.getMessage());
	}
}
