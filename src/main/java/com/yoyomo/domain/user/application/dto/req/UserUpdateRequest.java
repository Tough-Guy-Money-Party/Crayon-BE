package com.yoyomo.domain.user.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequest {
    private String name;
    private String number;
}