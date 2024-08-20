package com.yoyomo.domain.recruitment.application.dto.response;

import com.yoyomo.domain.process.application.dto.response.ProcessResponseDTO;

import java.util.List;

public class RecruitmentResponseDTO {

    public record Response(
            String title,
            String generation,
            String position,
            List<ProcessResponseDTO.Response> processes,
            int processCount
    ) {}
}
