package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ApplicationListResponse(
        UUID id,
        String name,
        String email,
        String tel,
        Status status,
        LocalDateTime createdAt
) {
    public static List<ApplicationListResponse> toResponse(List<Application> applications, Map<UUID, Status> status) {
        return applications.stream()
                .map(application -> toResponse(application, status.getOrDefault(application.getId(), Status.BEFORE_EVALUATION)))
                .toList();
    }

    public static Page<ApplicationListResponse> toResponse(Page<Application> applications, Map<UUID, Status> status) {
        return applications.map(application -> toResponse(application, status.getOrDefault(application.getId(), Status.BEFORE_EVALUATION)));
    }

    private static ApplicationListResponse toResponse(Application application, Status status) {
        return new ApplicationListResponse(
                application.getId(),
                application.getUserName(),
                application.getEmail(),
                application.getTel(),
                status,
                application.getCreatedAt()
        );
    }
}
