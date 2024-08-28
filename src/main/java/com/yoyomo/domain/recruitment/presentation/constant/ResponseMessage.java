package com.yoyomo.domain.recruitment.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_SAVE("모집 생성에 성공했습니다."),
    SUCCESS_READ("모집 조회에 성공했습니다."),
    SUCCESS_UPDATE("모집 수정에 성공했습니다."),
    SUCCESS_DELETE("모집 마감에 성공했습니다."),
    SUCCESS_CANCEL("모집 취소에 성공했습니다."),
    SUCCESS_ACTIVATE("모집 활성화에 성공했습니다."),
    SUCCESS_READ_PROCESSES("프로세스 목록 조회에 성공했습니다."),

    RECRUITMENT_CANNOT_UPDATE("진행중인 모집은 수정할 수 없습니다."),
    PROCESS_NOT_FOUND("존재하지 않는 모집입니다."),
    RECRUITMENT_NOT_FOUND("존재하지 않는 모집입니다."),
    EMPTY_PROCESS("프로세스가 존재하지 않는 모집입니다."),
    ACCESS_DENIED("권한이 없습니다.");
    private final String message;
}
