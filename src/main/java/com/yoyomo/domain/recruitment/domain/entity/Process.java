package com.yoyomo.domain.recruitment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    private int order;
    private processType type;
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDateTime announceAt;
    private int templateId;
}
