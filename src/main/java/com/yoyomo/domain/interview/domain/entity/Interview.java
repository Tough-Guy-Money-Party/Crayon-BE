package com.yoyomo.domain.interview.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    
    @NotNull
    private String place;

    @NotNull
    private LocalDateTime date;
}
