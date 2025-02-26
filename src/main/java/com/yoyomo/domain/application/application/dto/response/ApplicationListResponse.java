package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
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

    public static Page<ApplicationListResponse> toResponse(Page<Application> applications, Map<UUID, Status> status) {
        return applications.map(application -> toResponse(application, status.getOrDefault(application.getId(), Status.BEFORE_EVALUATION)));
    }

    public static Page<ApplicationListResponse> toResponse(Page<ApplicationWithStatus> applications) {
        return applications.map(ApplicationListResponse::toResponse);
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

    private static ApplicationListResponse toResponse(ApplicationWithStatus applicationWithStatus) {
        Application application = applicationWithStatus.application();
        return new ApplicationListResponse(
                application.getId(),
                application.getUserName(),
                application.getEmail(),
                application.getTel(),
                applicationWithStatus.status(),
                application.getCreatedAt()
        );
    }
}
