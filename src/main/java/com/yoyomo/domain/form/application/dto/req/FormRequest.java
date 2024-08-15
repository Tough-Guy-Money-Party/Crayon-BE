package com.yoyomo.domain.form.application.dto.req;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

import java.util.List;

public record FormRequest(
        String title,
        String description,
        List<ItemRequest> itemRequests
) {
}
