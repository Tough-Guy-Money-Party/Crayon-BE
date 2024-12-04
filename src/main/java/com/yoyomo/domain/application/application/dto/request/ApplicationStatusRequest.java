package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.entity.enums.Status;
import jakarta.validation.constraints.NotNull;

public record ApplicationStatusRequest(

        @NotNull
        Status status
) {
}
