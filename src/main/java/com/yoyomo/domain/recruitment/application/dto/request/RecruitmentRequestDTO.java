package com.yoyomo.domain.recruitment.application.dto.request;

import com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO;

import java.util.List;

public class RecruitmentRequestDTO {

    public record Save(
        String title,
        String position,
        String generation,
        String status,
        String formId,
        String clubId,
        List<ProcessRequestDTO.Save> processes
    ) {}
}
