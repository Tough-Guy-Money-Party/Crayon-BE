package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.item.application.dto.res.OptionResponse;
import com.yoyomo.domain.item.domain.entity.type.Type;

import java.util.List;

public record Answer(
        String title,
        Type type,
        String description,
        Integer order,
        Boolean required,
        List<OptionResponse> options,
        String answer,
        Integer maxLength,
        String meaningOfHigh,
        String meaningOfLow,
        Integer score
) {
}
