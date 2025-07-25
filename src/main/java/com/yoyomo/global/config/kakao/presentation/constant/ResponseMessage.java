package com.yoyomo.global.config.kakao.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
	KAKAO_TOKEN_ERROR("카카오 토큰을 가져오는 중에 오류가 발생했습니다."),
	KAKAO_USER_INFO_ERROR("카카오 사용자 정보를 가져오는 중에 오류가 발생했습니다.");
	private String message;
}

