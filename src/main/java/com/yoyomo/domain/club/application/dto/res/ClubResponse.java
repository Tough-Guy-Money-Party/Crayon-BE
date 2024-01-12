package com.yoyomo.domain.club.application.dto.res;

import lombok.Builder;

@Builder
public record ClubResponse(
        String name,
        String description
) {
}
