package com.yoyomo.domain.landing.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateSubDomainRequest(
        @NotNull
        String subDomain
) {
}
