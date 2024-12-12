package com.yoyomo.domain.application.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ApplicationMoveRequest(
        @NotNull Long fromProcessId,
        @NotNull Long toProcessId
) {
}
