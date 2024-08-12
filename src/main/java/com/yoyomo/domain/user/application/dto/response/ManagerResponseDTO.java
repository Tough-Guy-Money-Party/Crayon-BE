package com.yoyomo.domain.user.application.dto.response;

import com.yoyomo.global.config.jwt.presentation.JwtResponse;

public class ManagerResponseDTO {

    public record Response(
            Long id,
            String name,
            String email,
            JwtResponse token
    ) {}

}
