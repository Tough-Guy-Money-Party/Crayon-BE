package com.yoyomo.domain.item.application.dto.req;

import com.yoyomo.domain.item.domain.entity.Option;

public record OptionRequest(
        String id,
        String title,
        boolean selected
) {

    public Option toOption() {
        return new Option(id, title, selected);
    }
}
