package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ApplicationRequestDTO {

    public record Save(
            @NotEmpty String name,
            @NotEmpty String tel,
            @NotEmpty String email,
            @Valid List<ItemRequest> answers
    ) {}

    public record Update(
            @Valid List<ItemRequest> answers
    ) {}

    public record Stage(
            @NotNull List<String> ids,
            @NotNull Integer from,
            @NotNull Integer to
    ) {}
}
