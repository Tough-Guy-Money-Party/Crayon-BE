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
        @NotEmpty Integer generation,
        @NotNull Status status,
        @NotEmpty String formId,
        @NotEmpty String clubId,
        @Valid List<ProcessRequestDTO.Save> processes
    ) {}

    public record Update(
            @NotEmpty String title,
            @NotEmpty String position,
            @NotNull Integer generation,
            @NotNull Boolean isActive,
            @NotEmpty String formId,
            @Valid List<ProcessRequestDTO.Update> processes
    ) {}
}
