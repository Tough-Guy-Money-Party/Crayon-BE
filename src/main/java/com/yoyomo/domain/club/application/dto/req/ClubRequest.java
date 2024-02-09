package com.yoyomo.domain.club.application.dto.req;

import com.yoyomo.domain.user.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

public record ClubRequest(
        String name,
        String subDomain,
        String description
) {
}
