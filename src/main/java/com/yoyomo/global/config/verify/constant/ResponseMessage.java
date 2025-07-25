package com.yoyomo.global.config.verify.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
	INVALID_CODE("이메일 인증 코드가 올바르지 않습니다.");

	private final String message;
}
