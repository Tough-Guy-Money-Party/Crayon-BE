package com.yoyomo.domain.form.application.dto.request;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class FormRequestDTO {

    public record Save(
            @NotEmpty String title,
            @NotEmpty String description,
            @Valid List<ItemRequest> itemRequests
    ) {}

    public record Update(
            @NotEmpty String title,
            @NotEmpty String description,
            @Valid List<ItemRequest> itemRequests,
            @NotNull Boolean enabled
    ) {}
}
