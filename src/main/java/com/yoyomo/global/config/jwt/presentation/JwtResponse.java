package com.yoyomo.global.config.jwt.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String accessToken;
    private final String refreshToken;
}