package com.yoyomo.domain.landing.application.dto.response;

import com.yoyomo.domain.landing.domain.constant.DisplayMode;
import com.yoyomo.domain.landing.domain.entity.Landing;

public class LandingResponse {
	public record General(
		String subDomain,
		String siteName,
		String notionPageLink,
		String favicon,
		String image
	) {
	}

	public record Style(
		String callToAction,
		String buttonColor,
		String textColor,
		DisplayMode displayMode
	) {
	}

	public record All(
		String siteTitle,
		String notionPageLink,
		String favicon,
		String image,
		String callToAction,
		String buttonColor,
		String textColor,
		DisplayMode displayMode
	) {
		public static All toAll(Landing landing, String parsedNotionPageLink) {

			return new All(
				landing.getSiteName(),
				parsedNotionPageLink,
				landing.getFavicon(),
				landing.getImage(),
				landing.getCallToAction(),
				landing.getButtonColor(),
				landing.getTextColor(),
				landing.getDisplayMode()
			);
		}
	}
}
