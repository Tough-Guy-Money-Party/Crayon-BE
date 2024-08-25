package com.yoyomo.domain.club.application.dto.res;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubLandingStyle;
import com.yoyomo.domain.club.domain.entity.DisplayMode;
import com.yoyomo.domain.club.exception.InvalidNotionLinkException;
import com.yoyomo.global.config.s3.S3Service;
import lombok.Builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public record ClubAllSettingsResponse (
        String siteTitle,
        String notionPageLink,
        String favicon,
        String image,
        String callToAction,
        String buttonColor,
        String textColor,
        DisplayMode displayMode
)
{


    public static ClubAllSettingsResponse of(Club club, ClubLandingStyle clubLandingStyle) {
        return ClubAllSettingsResponse.builder()
                .siteTitle(club.getSiteTitle())
                .notionPageLink(S3Service.notionParser(club.getNotionPageLink()))
                .favicon(club.getFavicon())
                .image(club.getImage())
                .callToAction(clubLandingStyle.getCallToAction())
                .buttonColor(clubLandingStyle.getButtonColor())
                .textColor(clubLandingStyle.getTextColor())
                .displayMode(clubLandingStyle.getDisplayMode())
                .build();
    }
}
