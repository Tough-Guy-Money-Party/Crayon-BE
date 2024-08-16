package com.yoyomo.domain.club.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.ACCESS_DENIED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ClubAccessDeniedException extends ApplicationException {
    public ClubAccessDeniedException() {
        super(FORBIDDEN.value(), ACCESS_DENIED.getMessage());
    }
}