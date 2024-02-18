package com.yoyomo.global.config.participation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    ALREADY_PARTICIPATED("이미 참여하고 있는 동아리 입니다."),
    INVALID_CODE("유효하지 않은 코드입니다.")
    ;
    private String message;
}
