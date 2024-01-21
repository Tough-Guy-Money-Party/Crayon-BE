package com.yoyomo.domain.form.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("지원폼 생성에 성공했습니다."),
    SUCCESS_READ("지원폼 조회에 성공했습니다."),
    SUCCESS_UPDATE("지원폼 수정에 성공했습니다."),
    SUCCESS_DELETE("지원폼 삭제에 성공했습니다."),

    FORM_NOT_FOUND("존재하지 않는 지원폼입니다."),
    ;
    private String message;
}
