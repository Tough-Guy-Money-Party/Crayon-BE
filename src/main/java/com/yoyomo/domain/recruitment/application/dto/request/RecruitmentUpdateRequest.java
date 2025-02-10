package com.yoyomo.domain.recruitment.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RecruitmentUpdateRequest(

        @NotNull
        String title,

        @NotNull
        String position,

        @NotNull
        LocalDateTime startAt,

        @NotNull
        LocalDateTime endAt
) {
}
