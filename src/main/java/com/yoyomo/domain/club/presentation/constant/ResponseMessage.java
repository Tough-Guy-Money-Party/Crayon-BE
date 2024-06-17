package com.yoyomo.domain.club.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("동아리 생성에 성공했습니다."),
    SUCCESS_READ("동아리 조회에 성공했습니다."),
    SUCCESS_UPDATE("동아리 수정에 성공했습니다."),
    SUCCESS_DELETE("동아리 삭제에 성공했습니다."),
    SUCCESS_GET_CODE("동아리 관리자 참여 코드 조회를 성공했습니다."),
    SUCCESS_REGENERATE_CODE("동아리 관리자 참여 재생성을 성공했습니다."),
    SUCCESS_ADD_MANAGER("동아리 관리자 추가에 성공했습니다."),
    SUCCESS_REMOVE_MANAGER("동아리 관리자 삭제에 성공했습니다."),
    SUCCESS_GET_MANAGERS("동아리 관리자 조회에 성공했습니다."),

    CLUB_NOT_FOUND("존재하지 않는 동아리입니다."),
    ;
    private String message;
}
