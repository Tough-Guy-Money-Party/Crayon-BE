package com.yoyomo.domain.interview.application.dto;

import java.time.LocalDateTime;

public record InterviewResponse(
        String place,
        LocalDateTime date
) {
}
