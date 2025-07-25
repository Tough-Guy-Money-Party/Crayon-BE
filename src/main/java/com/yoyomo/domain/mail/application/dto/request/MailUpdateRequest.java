package com.yoyomo.domain.mail.application.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record MailUpdateRequest(
	@NotNull LocalDateTime scheduledTime
) {
}
