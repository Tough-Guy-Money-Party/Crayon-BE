package com.yoyomo.domain.club.exception;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ClubAccessDeniedException extends ApplicationException {
	public ClubAccessDeniedException() {
		super(BAD_REQUEST.value(), ACCESS_DENIED.getMessage());
	}
}
