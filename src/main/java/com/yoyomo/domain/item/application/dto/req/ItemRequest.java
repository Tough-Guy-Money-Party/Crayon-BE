package com.yoyomo.domain.item.application.dto.req;

import com.yoyomo.domain.item.domain.entity.type.Type;

import java.util.List;

public record ItemRequest(
        String formId,
        String question,
        Type type,
        int order,
        boolean required,
        int limit,
        List<String> options
) {
}

