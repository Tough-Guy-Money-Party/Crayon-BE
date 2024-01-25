package com.yoyomo.domain.form.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Item;

import java.util.Map;

public record FormDetailsResponse(
        String name,
        Map<String, Item> items
) {
}
