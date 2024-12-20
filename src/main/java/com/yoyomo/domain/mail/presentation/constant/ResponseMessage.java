package com.yoyomo.domain.mail.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SCHEDULED_MAIL_UPLOAD_SUCCESS("예약 메일 설정에 성공했습니다."),
    DIRECT_MAIL_SEND_SUCCESS("메일 즉시 전송에 성공했습니다."),
    CANCEL_MAIL_SUCCESS("메일 예약 취소에 성공했습니다."),

    MAIL_DB_UPLOAD_FAIL("메일을 Dynamodb에 업로드하는 중 예외가 발생했습니다."),
    LAMBDA_INVOKE_FAIL("람다 함수를 호출하는 중에 예외가 발생했습니다."),
    MAIL_CANCEL_FAIL("메일 예약을 취소하는 중 예외가 발생했습니다."),
    MAIL_NOT_SCHEDULED("예약된 메일이 존재하지 않습니다");
    private final String message;
}
