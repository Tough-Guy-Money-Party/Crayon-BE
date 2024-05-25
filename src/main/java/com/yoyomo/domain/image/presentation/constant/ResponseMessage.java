package com.yoyomo.domain.image.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    IMAGE_SAVE_FAILURE("이미지 저장에 실패 했습니다."),
    IMAGE_NOT_FOUND("존재하지 않는 이미지입니다."),
    ;
    private String message;
}
