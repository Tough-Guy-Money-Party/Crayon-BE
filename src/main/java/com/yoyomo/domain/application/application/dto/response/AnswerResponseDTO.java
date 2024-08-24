package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.item.domain.entity.Item;

import java.util.List;

public class AnswerResponseDTO {

    public record Response(
            String id,
            List<Item> items
    ) {}
}
