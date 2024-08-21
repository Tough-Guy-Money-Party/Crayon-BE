package com.yoyomo.domain.application.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SUCCESS_READ_RESULT("모집 결과 조회에 성공했습니다."),
    APPLICATION_NOT_FOUND("지원 이력이 존재하지 않습니다."),
    ACCESS_DENIED("권한이 없습니다.");
    private final String message;
}
