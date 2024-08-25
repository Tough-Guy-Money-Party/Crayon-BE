package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import jakarta.validation.constraints.NotNull;

public class EvaluationRequestDTO {

    public record Save(
            @NotNull Rating rating,
            @NotNull Status status,
            String memo
    ) {}
}
