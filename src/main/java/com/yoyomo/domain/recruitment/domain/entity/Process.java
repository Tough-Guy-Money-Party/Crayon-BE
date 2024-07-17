package com.yoyomo.domain.recruitment.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoyomo.domain.application.domain.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime announceStartAt;
    private LocalDateTime announceEndAt;
    private List<Application> applications = new ArrayList<>();
    private int templateId;

    @JsonIgnore
    public boolean isNotAnnounced() {
        return now().isBefore(announceEndAt);
    }

    @JsonIgnore
    public void addApplication(Application application) {
        this.applications.add(application);
    }
}
