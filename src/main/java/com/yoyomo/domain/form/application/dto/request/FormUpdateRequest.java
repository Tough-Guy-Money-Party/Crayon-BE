package com.yoyomo.domain.form.application.dto.request;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

import java.util.List;

public record FormUpdateRequest(
        String title,
        String description,
        List<ItemRequest> itemRequests,
        boolean enabled
) {
}
