package com.yoyomo.domain.template.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

	SUCCESS_TEMPLATE_SAVE("메일 템플릿 저장에 성공했습니다."),
	SUCCESS_TEMPLATE_UPDATE("메일 템플릿 업데이트에 성공했습니다."),
	SUCCESS_TEMPLATE_READ("메일 템플릿 조회에 성공했습니다."),
	SUCCESS_TEMPLATE_DELETE("메일 템플릿 삭제에 성공했습니다."),

	TEMPLATE_NOT_FOUND("존재하지 않는 템플릿입니다.");

	private final String message;
}
