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
    SUCCESS_SAVE_EVALUATION("평가 생성에 성공했습니다."),
    SUCCESS_READ_EVALUATION("평가 조회에 성공했습니다."),
    SUCCESS_UPDATE_EVALUATION("평가 수정에 성공했습니다."),
    SUCCESS_DELETE_EVALUATION("평가 삭제에 성공했습니다."),
    SUCCESS_READ_RESULT("모집 결과 조회에 성공했습니다."),

    SUCCESS_SAVE_INTERVIEW_RECORD("면접 기록 생성에 성공했습니다."),

    SUCCESS_GENERATE_CODE("이메일 인증 코드 발송에 성공했습니다."),
    SUCCESS_VERIFY_CODE("이메일 인증에 성공했습니다."),

    OVER_DEADLINE("모집 기간이 아닙니다."),
    APPLICATION_NOT_FOUND("지원 이력이 존재하지 않습니다."),
    ANSWER_NOT_FOUND("답변 이력이 존재하지 않습니다."),
    EVALUATION_NOT_FOUND("평가 이력이 존재하지 않습니다."),
    EVALUATION_ALREADY_EXIST("이미 평가 이력이 존재합니다."),
    ACCESS_DENIED("권한이 없습니다.");
    private final String message;
}
