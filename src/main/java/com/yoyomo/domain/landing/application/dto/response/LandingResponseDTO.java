package com.yoyomo.domain.landing.application.dto.response;

import com.yoyomo.domain.landing.domain.constant.DisplayMode;

public class LandingResponseDTO {
    public record General(
            String subDomain,
            String siteTitle,
            String notionPageLink,
            String favicon,
            String image
    ) {}

    public record Style(
            String callToAction,
            String buttonColor,
            String textColor,
            DisplayMode displayMode
    ) {}
}
