package com.yoyomo.domain.user.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshRequest {
    private String refreshToken;
    private String email;
}

