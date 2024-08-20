package com.yoyomo.domain.recruitment.application.dto.request;

import com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO;
import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RecruitmentRequestDTO {

    public record Save(
        String title,
        String position,
        String generation,
        Status status,
        String formId,
        String clubId,
        List<ProcessRequestDTO.Save> processes
    ) {}

    public record Update(
            @NotEmpty String title,
            @NotEmpty String position,
            @NotNull Integer generation,
            @NotNull Boolean isActive,
            @NotEmpty String formId,
            @NotNull List<ProcessRequestDTO.Update> processes
    ) {}
}
