package com.yoyomo.domain.item.application.dto.req;

import com.yoyomo.domain.item.domain.entity.type.Type;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ItemRequest(
        @NotEmpty String title,
        @NotNull Type type,
        @NotEmpty String description,
        @NotNull int order,
        @NotNull boolean required,

        // for Select
        List<OptionRequest> options,

        // for Answer & Date
        String answer,
        int maxLength,

        // for Score
        String meaningOfHigh,
        String meaningOfLow,
        int score

) {
}

