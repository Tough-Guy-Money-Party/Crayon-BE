package com.yoyomo.domain.club.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.domain.club.presentation.constant.ResponseMessage.DUPLICATED_PARTICIPATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DuplicatedParticipationException extends ApplicationException {
    public DuplicatedParticipationException() {
        super(NOT_FOUND.value(), DUPLICATED_PARTICIPATION.getMessage());
    }
}
