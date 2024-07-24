package com.yoyomo.domain.club.application.dto.req;

import com.yoyomo.domain.club.domain.entity.DisplayMode;

public record UpdateStyleSettingsRequest(
        String callToAction,
        String buttonColor,
        String textColor,
        DisplayMode displayMode
) {
}
