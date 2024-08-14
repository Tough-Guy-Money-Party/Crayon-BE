package com.yoyomo.domain.item.application.dto.res;

public record OptionResponse(
        String id,
        String title,
        boolean selected
) {
}