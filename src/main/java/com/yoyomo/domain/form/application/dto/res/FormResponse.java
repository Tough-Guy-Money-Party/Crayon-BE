package com.yoyomo.domain.form.application.dto.res;

import java.time.LocalDateTime;

public record FormResponse(
        String id,
        String title,
        String description,
        Boolean enabled,
        LocalDateTime createdAt
) {
}
