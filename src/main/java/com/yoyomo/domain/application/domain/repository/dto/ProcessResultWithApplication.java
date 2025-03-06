package com.yoyomo.domain.application.domain.repository.dto;

import com.yoyomo.domain.application.domain.entity.enums.Status;

import java.util.UUID;

public record ProcessResultWithApplication(
        Status status,
        UUID applicationId
) {

    public ProcessResultWithApplication {
        if (status == null) {
            status = Status.BEFORE_EVALUATION;
        }
    }
}
