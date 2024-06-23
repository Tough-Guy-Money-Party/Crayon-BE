package com.yoyomo.domain.form.application.dto.res;

import com.yoyomo.domain.item.application.dto.res.ItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public record FormDetailsResponse(
        String title,
        String description,
        List<ItemResponse> items,
        boolean active,
        LocalDateTime createdAt
) {
}
