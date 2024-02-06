package com.yoyomo.domain.form.application.dto.req;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

import java.util.List;

public record FormUpdateRequest(
        String name,
        List<ItemRequest> itemRequests
) {
}
