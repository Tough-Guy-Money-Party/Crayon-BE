package com.yoyomo.domain.user.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequest {
    private String accessToken;
    private String name;
    private String number;
}
