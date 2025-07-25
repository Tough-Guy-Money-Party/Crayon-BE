package com.yoyomo.global.config.kakao.exception;

import static com.yoyomo.global.config.kakao.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class KakaoUserInfoException extends ApplicationException {
	public KakaoUserInfoException() {
		super(INTERNAL_SERVER_ERROR.value(), KAKAO_USER_INFO_ERROR.getMessage());
	}
}
