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
        // for Text
        int limit,
        // for Select
        List<OptionRequest> options,
        // for Stage
        String meaningOfHigh,
        String meaningOfLow
) {
}

