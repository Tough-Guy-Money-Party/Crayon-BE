package com.yoyomo.domain.landing.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
	SUCCESS_READ("랜딩 페이지 조회에 성공했습니다"),
	SUCCESS_UPDATE("랜딩 페이지 업데이트에서 성공했습니다"),
	LANDING_NOT_FOUND("랜딩 페이지 정보를 찾을 수 없습니다"),
	INVALID_FORMAT("영어만 사용할 수 있습니다.");

	private final String message;
}
