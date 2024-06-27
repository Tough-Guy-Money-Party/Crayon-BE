package com.yoyomo.global.config.kakao.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.global.config.kakao.presentation.constant.ResponseMessage.KAKAO_USER_INFO_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class KakaoUserInfoException extends ApplicationException {
    public KakaoUserInfoException() {
        super(INTERNAL_SERVER_ERROR.value(), KAKAO_USER_INFO_ERROR.getMessage());
    }
}