package com.yoyomo.domain.interview.application.dto;

import java.time.LocalDateTime;

public record InterviewRequest(
        String place,
        LocalDateTime date
) {
}
