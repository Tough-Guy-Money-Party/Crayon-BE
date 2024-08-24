package com.yoyomo.domain.application.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SUCCESS_SAVE("모집 지원에 성공했습니다."),
    SUCCESS_READ_ALL("지원서 목록 조회에 성공했습니다."),
    SUCCESS_READ("지원서 조회에 성공했습니다."),
    SUCCESS_SEARCH("지원서 검색에 성공했습니다."),
    SUCCESS_UPDATE("지원서 수정에 성공했습니다."),
    SUCCESS_SAVE_INTERVIEW("면접 설정에 성공했습니다."),

    SUCCESS_READ_RESULT("모집 결과 조회에 성공했습니다."),
    APPLICATION_NOT_FOUND("지원 이력이 존재하지 않습니다."),
    ANSWER_NOT_FOUND("답변 이력이 존재하지 않습니다."),
    ACCESS_DENIED("권한이 없습니다.");
    private final String message;
}
