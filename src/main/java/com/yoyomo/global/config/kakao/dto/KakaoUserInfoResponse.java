package com.yoyomo.global.config.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {
	private Long id;
	private String connectedAt;
	private KakaoProperties properties;
	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;
}
