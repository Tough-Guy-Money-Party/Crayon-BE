package com.yoyomo.domain.club.application.dto.request;

import java.util.UUID;

public record ClubManagerUpdateDto(
        UUID clubId,
        String email
) {
}
