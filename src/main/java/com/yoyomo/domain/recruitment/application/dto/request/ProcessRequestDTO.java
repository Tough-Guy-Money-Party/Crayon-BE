package com.yoyomo.domain.recruitment.application.dto.request;

import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ProcessRequestDTO {

    public record Save(
            @NotEmpty String title,
            @NotNull Integer stage,
            @NotNull Type type,
            @NotNull LocalDateTime startAt,
            @NotNull LocalDateTime endAt,
            @NotNull LocalDateTime announceStartAt,
            @NotNull LocalDateTime announceEndAt
    ) {}

    public record Update(
            @NotEmpty String title,
            @NotNull Integer stage,
            @NotNull Type type,
            @NotNull LocalDateTime startAt,
            @NotNull LocalDateTime endAt,
            @NotNull LocalDateTime announceStartAt,
            @NotNull LocalDateTime announceEndAt
    ) {}
}
