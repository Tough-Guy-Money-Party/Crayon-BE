package com.yoyomo.domain.user.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestRegisterRequest {
    private String email;
    private String name;
    private String number;
}
