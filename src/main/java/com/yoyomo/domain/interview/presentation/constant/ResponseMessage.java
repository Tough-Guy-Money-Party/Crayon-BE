package com.yoyomo.domain.interview.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("면접 생성에 성공했습니다."),
    SUCCESS_READ("면접 조회에 성공했습니다."),
    SUCCESS_UPDATE("면접 수정에 성공했습니다."),
    SUCCESS_DELETE("면접 삭제에 성공했습니다."),

    APPLICATION_NOT_FOUND("존재하지 않는 면접입니다."),
    ALREADY_SUBMIT("이미 제출한 면접입니다."),
    ;
    private String message;
}
