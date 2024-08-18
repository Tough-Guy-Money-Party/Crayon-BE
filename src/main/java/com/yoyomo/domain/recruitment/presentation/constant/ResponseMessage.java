package com.yoyomo.domain.recruitment.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_SAVE("모집 생성에 성공했습니다."),
    DUPLICATE_USERNAME("이미 사용 중인 아이디입니다."),
    ACCESS_DENIED("권한이 없습니다.");
    private final String message;
}
