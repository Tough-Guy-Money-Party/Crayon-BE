package com.yoyomo.global.config.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoProperties {
	private String nickname;
	@JsonProperty("profile_image")
	private String profileImage;
	@JsonProperty("thumbnail_image")
	private String thumbnailImage;
}
