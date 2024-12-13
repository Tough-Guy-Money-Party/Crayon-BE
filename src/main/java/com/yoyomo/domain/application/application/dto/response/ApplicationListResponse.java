package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import java.time.LocalDateTime;
import java.util.UUID;

public record ApplicationListResponse(
        UUID id,
        String name,
        String email,
        String tel,
        Status status,
        LocalDateTime createdAt
) {
    public static ApplicationListResponse toResponse(Application application) {
        return new ApplicationListResponse(
                application.getId(),
                application.getUserName(),
                application.getEmail(),
                application.getTel(),
                application.getStatus(),
                application.getCreatedAt()
        );
    }
}
