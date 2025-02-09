package com.yoyomo.domain.item.application.dto.res;

import com.yoyomo.domain.item.domain.entity.Option;
import lombok.Builder;

@Builder
public record OptionResponse(
        String id,
        String title,
        boolean selected
) {
    public static OptionResponse toResponse(Option option) {
        return OptionResponse.builder()
                .id(option.getId())
                .title(option.getTitle())
                .selected(option.isSelected())
                .build();
    }
}
