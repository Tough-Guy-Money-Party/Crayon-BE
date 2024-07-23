package com.yoyomo.domain.club.application.dto.res;

import com.yoyomo.domain.club.domain.entity.DisplayMode;

public record ClubStyleSettingsResponse(
        String callToAction,
        String buttonColor,
        String textColor,
        DisplayMode displayMode
) {
}
