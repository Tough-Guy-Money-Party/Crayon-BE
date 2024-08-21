package com.yoyomo.domain.form.application.dto.response;

import com.yoyomo.domain.item.application.dto.res.ItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public class FormResponseDTO {

    public record SaveResponse(
            String id
    ) {}

    public record DetailResponse(
            String title,
            String description,
            List<ItemResponse> items,
            Boolean enabled,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    public record Response(
            String id,
            String title,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
