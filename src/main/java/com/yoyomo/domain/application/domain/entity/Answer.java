package com.yoyomo.domain.application.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @NotBlank
    private String itemId;

    @NotBlank
    private String reply;
}
