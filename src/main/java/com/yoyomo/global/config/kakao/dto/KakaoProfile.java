package com.yoyomo.global.config.kakao.dto;

import lombok.Getter;

@Getter
public class KakaoProfile {
    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
    private boolean is_default_image;
    private boolean is_default_nickname;
}
