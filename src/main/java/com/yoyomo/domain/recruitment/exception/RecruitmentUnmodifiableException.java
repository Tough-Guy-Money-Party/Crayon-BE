package com.yoyomo.domain.recruitment.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.RECRUITMENT_CANNOT_UPDATE;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class RecruitmentUnmodifiableException extends ApplicationException {
    public RecruitmentUnmodifiableException() {
        super(NOT_ACCEPTABLE.value(), RECRUITMENT_CANNOT_UPDATE.getMessage());
    }
}
