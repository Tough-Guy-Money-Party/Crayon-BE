package com.yoyomo.domain.club.exception;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class DuplicatedParticipationException extends ApplicationException {
	public DuplicatedParticipationException() {
		super(NOT_FOUND.value(), DUPLICATED_PARTICIPATION.getMessage());
	}
}
