package com.yoyomo.domain.user.application.dto.res;

import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String number;
    private JwtResponse token;
}