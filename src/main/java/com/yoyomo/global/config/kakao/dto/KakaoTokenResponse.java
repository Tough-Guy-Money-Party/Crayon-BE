package com.yoyomo.global.config.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoTokenResponse {
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("expires_in")
	private Integer expiresIn;
	private String scope;
	@JsonProperty("refresh_token_expires_in")
	private Integer refreshTokenExpiresIn;
}
