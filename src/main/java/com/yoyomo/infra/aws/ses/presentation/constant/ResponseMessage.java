package com.yoyomo.infra.aws.ses.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SUCCESS_TEMPLATE_SAVE("인증 메일용 템플릿 저장에 성공했습니다."),
    SUCCESS_TEMPLATE_UPDATE("인증 메일용 템플릿 업데이트에 성공했습니다."),
    SUCCESS_TEMPLATE_READ("인증 메일용 템플릿 조회에 성공했습니다.");

    private final String message;
}
