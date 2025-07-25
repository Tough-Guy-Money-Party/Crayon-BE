package com.yoyomo.domain.club.exception;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ClubManagerNotFoundException extends ApplicationException {
	public ClubManagerNotFoundException() {
		super(NOT_FOUND.value(), CLUB_MANAGER_NOT_FOUND.getMessage());
	}
}
