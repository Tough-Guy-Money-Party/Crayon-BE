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
    SUCCESS_DELETE_MANAGERS("동아리 관리자 삭제에 성공했습니다."),
    SUCCESS_PARTICIPATION("동아리 관리자 추가에 성공했습니다."),
    SUCCESS_UPDATE_MANAGERS("동아리 관리자 수정에 성공했습니다"),
    SUCCESS_READ_CODE("동아리 코드 조회에 성공했습니다."),
    SUCCESS_UPDATE_CODE("동아리 코드 재생성에 성공했습니다."),
    SUCCESS_CREATE_SUBDOMAIN("서브도메인 생성에 성공했습니다"),

    ACCESS_DENIED("잘못된 접근입니다."),
    DUPLICATED_PARTICIPATION("이미 참여한 동아리입니다."),
    DUPLICATED_SUBDOMAIN("이미 존재하는 도메인입니다."),
    CLUB_MANAGER_NOT_FOUND("동아리에 속한 관리자가 아닙니다."),
    CLUB_NOT_FOUND("동아리 조회에 실패했습니다.");

    private final String message;
}
