package com.yoyomo.domain.item.application.dto.req;

import com.yoyomo.domain.item.domain.entity.type.Type;

import java.util.List;

public record ItemRequest(
        String question,
        Type type,
        int order,
        boolean required,
        // for Text
        int limit,
        // for Select
        List<String> options,
        // for Stage
        String meaning_of_high,
        String meaning_of_low
) {
}

