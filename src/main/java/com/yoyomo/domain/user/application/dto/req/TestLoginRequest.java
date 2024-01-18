package com.yoyomo.domain.user.application.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class TestLoginRequest {
    private String email;
}

