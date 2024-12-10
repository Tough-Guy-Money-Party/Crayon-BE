package com.yoyomo.domain.application.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ApplicationMoveRequest(
        @NotNull long fromProcessId,
        @NotNull long toProcessId
) {
}
