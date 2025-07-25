package com.yoyomo.domain.landing.application.dto.request;

import com.yoyomo.domain.landing.domain.constant.DisplayMode;

public class LandingRequest {

	public record Save(
		String clubId,
		String favicon,
		String image,
		String siteName,
		String callToAction,
		String buttonColor,
		String textColor
	) {
	}

	public record NotionSave(
		String clubId,
		String notionPageLink
	) {
	}

	public record Style(
		String clubId,
		String callToAction,
		String buttonColor,
		String textColor,
		DisplayMode displayMode
	) {
	}

	public record General(
		String clubId,
		String subDomain,
		String siteName,
		String notionPageLink,
		String favicon,
		String image
	) {
	}
}
