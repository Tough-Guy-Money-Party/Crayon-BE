package com.yoyomo.domain.user.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
	SUCCESS_REGISTER("회원가입에 성공했습니다."),
	SUCCESS_LOGIN("로그인에 성공했습니다."),
	SUCCESS_UPDATE("유저 수정에 성공했습니다."),
	SUCCESS_DELETE("유저 삭제에 성공했습니다."),
	SUCCESS_REFRESH("토큰 재발급에 성공했습니다."),
	SUCCESS_GET_CLUBS("워크스페이스 조회에 성공했습니다."),
	USER_NOT_FOUND("존재하지 않는 유저입니다."),
	NEED_REGISTER("회원가입이 필요한 유저입니다."),
	DUPLICATE_USERNAME("이미 사용 중인 아이디입니다."),
	ACCESS_DENIED("권한이 없습니다."),
	ANONYMOUS_USER("인증 정보가 존재하지 않습니다."),
	SUCCESS_REISSUE_TOKEN("Jwt 토큰 재발급에 성공했습니다.");

	private String message;
}
