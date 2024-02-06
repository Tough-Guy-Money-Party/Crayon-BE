package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.ApplicationStatus;
import com.yoyomo.domain.user.application.dto.res.UserApplicationResponse;

public record ApplicationResponse(
        String id,
        UserApplicationResponse user,
        ApplicationStatus applicationStatus
) {
}
