package com.yoyomo.domain.recruitment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
