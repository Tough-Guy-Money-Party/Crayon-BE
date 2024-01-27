package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.ApplicationStatus;

public record ApplicationResponse(

        String id,
        ApplicationStatus applicationStatus
) {
}
