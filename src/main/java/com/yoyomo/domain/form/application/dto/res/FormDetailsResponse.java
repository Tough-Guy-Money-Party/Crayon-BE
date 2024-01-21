package com.yoyomo.domain.form.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Item;

import java.util.List;
import java.util.Map;

public record FormDetailsResponse(
        String name,
        List<String> process,
        Map<String, Item> items
) {
}
