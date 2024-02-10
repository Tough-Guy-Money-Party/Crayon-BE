package com.yoyomo.domain.application.application.dto.req;

import com.yoyomo.domain.application.domain.entity.ApplicationStatus;

public record ApplicationStatusRequest(
        ApplicationStatus applicationStatus,
        String process
) {
}
