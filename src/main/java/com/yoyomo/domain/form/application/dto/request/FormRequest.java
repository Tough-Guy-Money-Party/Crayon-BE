package com.yoyomo.domain.form.application.dto.request;

import java.util.List;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class FormRequest {

	public record Save(
		@NotEmpty String title,
		String description,
		@Valid List<ItemRequest> itemRequests
	) {
	}

	public record Update(
		@NotEmpty String title,
		String description,
		@Valid List<ItemRequest> itemRequests
	) {
	}
}
