package com.yoyomo.global.config.kakao.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {
    private Long id;
    private String connectedAt;
    private KakaoProperties properties;
    private KakaoAccount kakao_account;
}
