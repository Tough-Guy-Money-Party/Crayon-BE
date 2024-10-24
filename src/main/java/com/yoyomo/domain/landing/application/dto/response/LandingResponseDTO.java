package com.yoyomo.domain.landing.application.dto.response;

public class LandingResponseDTO {
    public record General(
            String subDomain,
            String siteName,
            String notionPageLink,
            String favicon,
            String image
    ) {}
}
