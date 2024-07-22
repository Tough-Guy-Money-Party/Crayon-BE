package com.yoyomo.domain.club.application.dto.res;

public record ClubGeneralSettingResponse(
        String subDomain,
        String siteTitle,
        String notionPageLink,
        String favicon,
        String image
) {
}
