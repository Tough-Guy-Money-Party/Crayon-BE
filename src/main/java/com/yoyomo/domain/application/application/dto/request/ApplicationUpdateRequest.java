package com.yoyomo.domain.application.application.dto.request;

import java.util.List;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

import jakarta.validation.Valid;

public record ApplicationUpdateRequest(
	@Valid List<ItemRequest> answers
) {
}
