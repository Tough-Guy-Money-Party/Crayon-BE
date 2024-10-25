package com.yoyomo.infra.aws.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    DISTRIBUTE_NOT_FOUND("배포를 찾을 수 없습니다.");
    private String message;
}
