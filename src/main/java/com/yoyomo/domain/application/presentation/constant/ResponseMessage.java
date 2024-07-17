package com.yoyomo.domain.application.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("지원서 생성에 성공했습니다."),
    SUCCESS_READ("지원서 조회에 성공했습니다."),
    SUCCESS_UPDATE("지원서 수정에 성공했습니다."),
    SUCCESS_DELETE("지원서 삭제에 성공했습니다."),

    PROCESS_NOT_FOUND("존재하지 않는 프로세스입니다."),
    APPLICATION_NOT_FOUND("존재하지 않는 지원서입니다."),
    ALREADY_SUBMIT("이미 제출한 지원서입니다."),
    ;
    private String message;
}
