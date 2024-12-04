package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import jakarta.validation.Valid;

import java.util.List;

public record ApplicationUpdateRequest(
        @Valid List<ItemRequest> answers
) {
}
