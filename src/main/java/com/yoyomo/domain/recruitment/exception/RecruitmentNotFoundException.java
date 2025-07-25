package com.yoyomo.domain.recruitment.exception;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class RecruitmentNotFoundException extends ApplicationException {
	public RecruitmentNotFoundException() {
		super(NOT_FOUND.value(), RECRUITMENT_NOT_FOUND.getMessage());
	}
}
