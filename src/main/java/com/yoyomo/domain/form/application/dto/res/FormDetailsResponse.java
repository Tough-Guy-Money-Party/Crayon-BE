package com.yoyomo.domain.form.application.dto.res;

import com.yoyomo.domain.item.application.dto.res.ItemResponse;

import java.util.List;

public record FormDetailsResponse(
        String name,
        String description,
        String instruction,
        List<ItemResponse> items
) {
}
