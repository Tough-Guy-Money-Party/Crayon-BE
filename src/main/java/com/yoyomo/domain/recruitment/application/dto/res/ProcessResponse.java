package com.yoyomo.domain.recruitment.application.dto.res;

import com.yoyomo.domain.recruitment.domain.entity.ProcessType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProcessResponse(
        Integer stage,
        ProcessType type,
        String title,
        LocalDate startAt,
        LocalDate endAt,
        LocalDateTime announceStartAt,
        LocalDateTime announceEndAt,
        Integer applicantCount
) {}
