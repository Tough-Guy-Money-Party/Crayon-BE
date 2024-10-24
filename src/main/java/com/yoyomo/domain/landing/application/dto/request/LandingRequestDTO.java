package com.yoyomo.domain.landing.application.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class LandingRequestDTO {

    public record Save(
            String clubId,
            String favicon,
            String image,
            String siteTitle,
            String callToAction,
            String buttonColor,
            String textColor
    ) {}

    public record NotionSave(
            String clubId,
            String notionPageLink
    ) {
    }

    public record Style(
            String clubId,
            String callToAction,
            String buttonColor,
            String textColor
    ) {}

    public record General(
            String clubId,
            String subDomain,
            String siteName,
            String notionPageLink,
            String favicon,
            String image
    ) {}
}
