package com.yoyomo.domain.recruitment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private int templateId;
}
