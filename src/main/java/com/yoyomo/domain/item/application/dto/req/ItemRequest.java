package com.yoyomo.domain.item.application.dto.req;

import com.yoyomo.domain.item.domain.entity.Option;
import com.yoyomo.domain.item.domain.entity.type.Type;

import java.util.List;

public record ItemRequest(
        String title,
        Type type,
        String description,
        int order,
        boolean required,

        // for Select
        List<OptionRequest> options,
        // for Answer
        String answer,
        int maxLength,
        // for Score
        String meaningOfHigh,
        String meaningOfLow,
        int score


) {
}

