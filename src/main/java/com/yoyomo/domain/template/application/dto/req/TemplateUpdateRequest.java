package com.yoyomo.domain.template.application.dto.req;

public record TemplateUpdateRequest(
        String name,

        String passText,

        String failText
) {
}