package com.yoyomo.domain.recruitment.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private int stage;
    private ProcessType type;
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDateTime announceAt;
    private int templateId;

    @JsonIgnore
    public boolean isNotAnnounced() {
        return now().isBefore(announceAt);
    }
}
