package com.yoyomo.domain.club.application.dto.req;

import java.util.List;

public record RemoveManagerRequest(
        String clubId,
        List<String> userId
) {
}