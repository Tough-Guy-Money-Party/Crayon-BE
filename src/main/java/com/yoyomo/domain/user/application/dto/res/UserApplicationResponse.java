package com.yoyomo.domain.user.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApplicationResponse {
    private String name;
    private String phone;
    private String email;
}