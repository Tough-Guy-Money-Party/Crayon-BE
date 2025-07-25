package com.yoyomo.global.config.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class KakaoAccount {
	@JsonProperty("profile_nickname_needs_agreement")
	private boolean profileNicknameNeedsAgreement;
	@JsonProperty("profile_image_needs_agreement")
	private boolean profileImageNeedsAgreement;
	private KakaoProfile profile;
	@JsonProperty("has_email")
	private boolean hasEmail;
	@JsonProperty("email_needs_agreement")
	private boolean emailNeedsAgreement;
	@JsonProperty("is_email_valid")
	private boolean isEmailValid;
	@JsonProperty("is_email_verified")
	private boolean isEmailVerified;
	private String email;
}
