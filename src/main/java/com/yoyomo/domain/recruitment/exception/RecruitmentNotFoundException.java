package com.yoyomo.domain.recruitment.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.user.presentation.constant.ResponseMessage.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RecruitmentNotFoundException extends ApplicationException {
    public RecruitmentNotFoundException() {
        super(NOT_FOUND.value(), USER_NOT_FOUND.getMessage());
    }
}