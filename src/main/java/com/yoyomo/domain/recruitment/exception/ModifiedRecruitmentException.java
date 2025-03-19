package com.yoyomo.domain.recruitment.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.MODIFIED_RECRUITMENT;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class ModifiedRecruitmentException extends ApplicationException {
    public ModifiedRecruitmentException() {
        super(NOT_ACCEPTABLE.value(), MODIFIED_RECRUITMENT.getMessage());
    }
}
