package com.yoyomo.global.config.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoProfile {
	private String nickname;
	@JsonProperty("thumbnail_image_url")
	private String thumbnailImageUrl;
	@JsonProperty("profile_image_url")
	private String profileImageUrl;
	@JsonProperty("is_default_image")
	private boolean isDefaultImage;
	@JsonProperty("is_default_nickname")
	private boolean isDefaultNickname;
}
