package com.yoyomo.domain.club.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.CLUB_MANAGER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ClubManagerNotFoundException extends ApplicationException {
    public ClubManagerNotFoundException() {
        super(NOT_FOUND.value(), CLUB_MANAGER_NOT_FOUND.getMessage());
    }
}
