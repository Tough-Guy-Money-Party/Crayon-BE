package com.yoyomo.domain.template.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("템플릿 생성에 성공했습니다."),
    SUCCESS_READ("템플릿 조회에 성공했습니다."),
    SUCCESS_UPDATE("템플릿 수정에 성공했습니다."),
    SUCCESS_DELETE("템플릿 삭제에 성공했습니다."),

    TEMPLATE_NOT_FOUND("존재하지 않는 템플릿입니다."),
    ;
    private String message;
}
