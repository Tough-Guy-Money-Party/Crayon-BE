package com.yoyomo.domain.user.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestLoginRequest {
    private String email;
    private String empty;
}
