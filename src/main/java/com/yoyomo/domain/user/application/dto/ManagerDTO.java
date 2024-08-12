package com.yoyomo.domain.user.application.dto;

import com.yoyomo.global.config.jwt.presentation.JwtResponse;

public class ManagerDTO {

    public record Response(
            Long id,
            String name,
            String email,
            JwtResponse token
    ) {}
}
