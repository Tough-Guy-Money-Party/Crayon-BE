package com.yoyomo.domain.club.exception;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class DuplicatedSubDomainException extends ApplicationException {
	public DuplicatedSubDomainException() {
		super(CONFLICT.value(), DUPLICATED_SUBDOMAIN.getMessage());
	}
}
