package com.yoyomo.domain.application.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    @NotBlank
    private String managerId;

    @NotBlank
    private String managerName;

    @NotBlank
    private AssessmentRating assessmentRating;

    @NotBlank
    private String assessmentText;

    @NotBlank
    private LocalDateTime createdAt;
}
