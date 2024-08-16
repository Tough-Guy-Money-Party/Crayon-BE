package com.yoyomo.domain.club.application.dto.response;

public class ClubResponseDTO {

    public record Response(
            String id,
            String name,
            String subDomain
    ) {}
}
