package com.yoyomo.domain.club.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ClubRequestDTO {

    public record Save(
            @NotEmpty String name,
            @NotEmpty String subDomain
    ) {}

    public record Update(
            @NotEmpty String name
    ) {}

    public record Participation(
            @NotEmpty String code
    ) {}

    public record Delete(
            @NotEmpty String clubId,
            @NotNull List<Long> userIds
    ) {}
}
