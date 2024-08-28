package com.yoyomo.domain.recruitment.application.dto.request;

import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RecruitmentRequestDTO {

    public record Save(
        @NotEmpty String title,
        @NotEmpty String position,
        @NotEmpty String generation,
        @NotNull Status status,
        @NotEmpty String clubId,
        @Valid List<ProcessRequestDTO.Save> processes
    ) {}

    public record Update(
            @NotEmpty String title,
            @NotEmpty String position,
            @NotEmpty String generation,
            @NotNull Boolean isActive,
            @Valid List<ProcessRequestDTO.Update> processes
    ) {}
}
