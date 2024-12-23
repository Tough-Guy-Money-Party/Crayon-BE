package com.yoyomo.domain.form.application.dto.request;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class FormRequestDTO {

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
