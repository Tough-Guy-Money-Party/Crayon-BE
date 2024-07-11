package com.yoyomo.domain.recruitment.application.dto.res;

import java.time.LocalDateTime;

public record ProcessResultResponse(
        String clubName,
        String name,
        String recruitmentTitle,
        LocalDateTime date,
        String place
) {
}
