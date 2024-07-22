package com.yoyomo.domain.club.application.dto.req;

public record UpdateGeneralSettingsRequest(
        String subDomain,
        String siteTitle,
        String notionPageLink,
        String favicon,
        String image
) {
}
