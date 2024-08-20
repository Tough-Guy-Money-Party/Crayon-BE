package com.yoyomo.domain.process.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_SAVE("모집 생성에 성공했습니다."),
    SUCCESS_READ("모집 조회에 성공했습니다."),
    SUCCESS_UPDATE("모집 수정에 성공했습니다."),

    PROCESS_NOT_FOUND("존재하지 않는 모집입니다."),
    EMPTY_PROCESS("프로세스가 존재하지 않는 모집입니다."),
    ACCESS_DENIED("권한이 없습니다.");
    private final String message;
}
