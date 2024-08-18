package com.yoyomo.domain.form.application.dto.req;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

import java.util.List;

public class FormRequestDTO {

    public record Save(
            String title,
            String description,
            List<ItemRequest> itemRequests
    ) {}

    public record Update(
            String title,
            String description,
            List<ItemRequest> itemRequests,
            boolean enabled
    ) {}
}
