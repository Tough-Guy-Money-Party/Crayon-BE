package com.yoyomo.infra.notion.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    INVALID_LINK("유효하지 않은 링크입니다.")
    ;
    private String message;
}
