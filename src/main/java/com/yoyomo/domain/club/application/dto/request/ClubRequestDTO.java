package com.yoyomo.domain.club.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class ClubRequestDTO {

    public record Save(
            @NotEmpty String name
    ) {
    }

    public record Update(
            @NotEmpty String name
    ) {
    }

    public record Participation(
            @NotEmpty String code
    ) {
    }

    public record Delete(
            @NotNull UUID clubId,
            @NotNull List<Long> userIds
    ) {
    }
}
