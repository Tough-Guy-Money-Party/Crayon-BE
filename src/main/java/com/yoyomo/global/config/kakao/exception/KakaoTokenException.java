package com.yoyomo.global.config.kakao.exception;

import com.yoyomo.global.config.exception.ApplicationException;

import static com.yoyomo.global.config.kakao.presentation.constant.ResponseMessage.KAKAO_TOKEN_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class KakaoTokenException extends ApplicationException {
    public KakaoTokenException() {
        super(INTERNAL_SERVER_ERROR.value(),KAKAO_TOKEN_ERROR.getMessage());
    }
}