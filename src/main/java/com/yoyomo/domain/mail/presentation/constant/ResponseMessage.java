package com.yoyomo.domain.mail.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    MAIL_DB_UPLOAD_FAIL("메일을 Dynamodb에 업로드하는 중 예외가 발생했습니다.");
    private final String message;
}
