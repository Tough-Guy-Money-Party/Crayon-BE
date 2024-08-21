package com.yoyomo.domain.process.application.dto.response;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.process.domain.entity.enums.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProcessResponseDTO {

    public record Response(
            Integer stage,
            Type type,
            String title,
            LocalDate startAt,
            LocalDate endAt,
            LocalDateTime announceStartAt,
            LocalDateTime announceEndAt,
            List<ApplicationResponseDTO.Response> applications
    ) {}
}
