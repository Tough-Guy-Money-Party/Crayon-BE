package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;

import java.util.List;

public class ApplicationResponseDTO {

    public record Response(
            String id,
            ClubResponseDTO.Response club,
            List<ProcessResponseDTO.Info> processes,
            Integer currentStage
    ) {
    }
}
