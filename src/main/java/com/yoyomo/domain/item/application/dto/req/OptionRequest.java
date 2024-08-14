package com.yoyomo.domain.item.application.dto.req;

public record OptionRequest(
        String id,
        String title,
        boolean selected
) {
}