package com.yoyomo.domain.recruitment.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.RECRUITMENT_DELETED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RecruitmentDeletedException extends ApplicationException {
    public RecruitmentDeletedException() {
        super(NOT_FOUND.value(), RECRUITMENT_DELETED.getMessage());
    }
}