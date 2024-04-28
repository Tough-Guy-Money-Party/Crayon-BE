package com.yoyomo.domain.template.application.dto.res;

import com.yoyomo.domain.item.application.dto.res.ItemResponse;

import java.util.List;

public record TemplateDetailsResponse(
        String name,

        String passText,

        String failText
) {
}