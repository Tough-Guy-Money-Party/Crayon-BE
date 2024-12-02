package com.yoyomo.domain.mail.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SCHEDULED_MAIL_UPLOAD_SUCCESS("예약 메일 설정에 성공했습니다."),
    DIRECT_MAIL_SEND_SUCCESS("메일 즉시 전송에 성공했습니다."),

    MAIL_DB_UPLOAD_FAIL("메일을 Dynamodb에 업로드하는 중 예외가 발생했습니다.");
    private final String message;
}
