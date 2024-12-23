package com.yoyomo.domain.mail.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MailUpdateRequest(
        @NotNull LocalDateTime scheduledTime
) {
}
