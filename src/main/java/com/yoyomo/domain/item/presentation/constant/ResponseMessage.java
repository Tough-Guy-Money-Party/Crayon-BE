package com.yoyomo.domain.item.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("항목 생성에 성공했습니다."),
    SUCCESS_READ("항목 조회에 성공했습니다."),
    SUCCESS_UPDATE("항목 수정에 성공했습니다."),
    SUCCESS_DELETE("항목 삭제에 성공했습니다."),

    ITEM_NOT_FOUND("존재하지 않는 항목입니다."),
    INVALID_ITEM("유효하지 않은 항목입니다.")
    ;
    private String message;
}
