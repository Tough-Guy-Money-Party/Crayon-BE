package com.yoyomo.domain.club.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_SAVE("동아리 생성에 성공했습니다."),
    SUCCESS_READ("동아리 조회에 성공했습니다."),
    SUCCESS_UPDATE("동아리 수정에 성공했습니다."),
    SUCCESS_DELETE("동아리 삭제에 성공했습니다."),
    SUCCESS_GET_MANAGERS("동아리 관리자 조회에 성공했습니다."),

    DUPLICATED_SUBDOMAIN("이미 존재하는 도메인입니다."),
    CLUB_NOT_FOUND("동아리 조회에 실패했습니다.");

    private String message;
}
