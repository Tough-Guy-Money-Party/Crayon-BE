package com.yoyomo.domain.application.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StageUpdateRequest(
        @NotNull List<String> ids,
        @NotNull Integer from,
        @NotNull Integer to
) {
}
