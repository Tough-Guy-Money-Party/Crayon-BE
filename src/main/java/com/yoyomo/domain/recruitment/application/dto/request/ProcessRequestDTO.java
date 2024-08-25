package com.yoyomo.domain.recruitment.application.dto.request;

import com.yoyomo.domain.recruitment.application.annotation.PeriodCheck;
import com.yoyomo.domain.recruitment.application.annotation.TimeCheck;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ProcessRequestDTO {

    public record Save(
            @NotEmpty String title,
            @NotNull Integer stage,
            @NotNull Type type,
            @Valid @PeriodCheck Period period
    ) {}

    public record Update(
            @NotEmpty String title,
            @NotNull Integer stage,
            @NotNull Type type,
            @Valid @PeriodCheck Period period
    ) {}

    public record Period(
        @Valid Evaluation evaluation,
        @Valid Announcement announcement
    ) {}

    public record Evaluation(
            @TimeCheck Time time
    ) {}

    public record Announcement(
            @TimeCheck Time time
    ) {}

    public record Time(
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {}
}
