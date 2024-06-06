package com.yoyomo.domain.club.application.dto.req;

public record RemoveManagerRequest(
        String clubId,
        String userId
) {
}