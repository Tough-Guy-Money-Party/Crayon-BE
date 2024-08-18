package com.yoyomo.domain.process.application.dto.request;

import com.yoyomo.domain.process.domain.entity.enums.Type;

import java.time.LocalDateTime;

public class ProcessRequestDTO {

    public record Save(
            String title,
            Integer stage,
            Type type,
            LocalDateTime startAt,
            LocalDateTime endAt,
            LocalDateTime announceStartAt,
            LocalDateTime announceEndAt
    ) {}
}
