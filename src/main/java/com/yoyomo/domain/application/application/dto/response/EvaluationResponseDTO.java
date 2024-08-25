package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;

public class EvaluationResponseDTO {

    public record Response(
            Long id,
            Rating rating,
            Status status,
            String managerName,
            String memo
    ) {}
}
