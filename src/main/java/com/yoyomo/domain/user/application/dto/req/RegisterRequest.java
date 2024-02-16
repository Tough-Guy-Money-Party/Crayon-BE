package com.yoyomo.domain.user.application.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
public class RegisterRequest {
    private String code;
    private String name;
}
