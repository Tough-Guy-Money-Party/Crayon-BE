package com.yoyomo.domain.club.application.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class ClubRequestDTO {

    public record Save(
            @NotEmpty String name,
            @NotEmpty String subDomain
    ) {}
}
