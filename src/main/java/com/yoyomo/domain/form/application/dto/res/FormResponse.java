package com.yoyomo.domain.form.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Item;

import java.util.List;

public record FormResponse(
        String name,
        List<Item> items
) {
}
