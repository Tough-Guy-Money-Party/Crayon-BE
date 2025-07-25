package com.yoyomo.domain.recruitment.exception;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ModifiedRecruitmentException extends ApplicationException {
	public ModifiedRecruitmentException() {
		super(NOT_ACCEPTABLE.value(), MODIFIED_RECRUITMENT.getMessage());
	}
}
