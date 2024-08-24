package com.yoyomo.domain.recruitment.application.dto.response;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;

import java.time.LocalDateTime;
import java.util.List;

public class ProcessResponseDTO {

    public record Response(
            Integer stage,
            Type type,
            String title,
            LocalDateTime startAt,
            LocalDateTime endAt,
            LocalDateTime announceStartAt,
            LocalDateTime announceEndAt,
            List<ApplicationResponseDTO.MyResponse> applications,
            Integer applicantCount
    ) {}

    public record Info(
            Integer stage,
            Type type,
            String title,
            LocalDateTime startAt,
            LocalDateTime endAt,
            LocalDateTime announceStartAt,
            LocalDateTime announceEndAt
    ) {}
}
