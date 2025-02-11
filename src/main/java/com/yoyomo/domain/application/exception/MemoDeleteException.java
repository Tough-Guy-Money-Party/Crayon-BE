package com.yoyomo.domain.application.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.application.presentation.constant.ResponseMessage.CANNOT_MEMO_DELETE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MemoDeleteException extends ApplicationException {
    public MemoDeleteException() {
        super(NOT_FOUND.value(), CANNOT_MEMO_DELETE.getMessage());
    }
}
