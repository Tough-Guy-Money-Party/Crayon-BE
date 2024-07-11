package com.yoyomo.domain.recruitment.application.dto.res;

import java.time.LocalDate;

public record ProcessResultsResponse(
        String recruitmentTitle,
        String processTitle,
        LocalDate startAt,
        LocalDate endAt
) {
}
